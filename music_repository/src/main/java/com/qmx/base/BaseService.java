package com.qmx.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.qmx.constants.Constants;
import com.qmx.redis.CacheUtil;
import com.qmx.util.DataUtil;
import com.qmx.util.ExceptionUtil;
import com.qmx.util.InstanceUtil;
import com.qmx.util.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务逻辑层基类<br/>
 * 继承基类后必须配置CacheConfig(cacheNames="")
 */
public abstract class BaseService<T extends BaseModel> implements ApplicationContextAware {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected IBaseMapper<T> mapper;
    protected ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 分页查询
     */
    public static Page<Long> getPage(Map<String, Object> params) {
        Integer current = 1;
        Integer size = 10;
        String orderBy = "id";
//        String role = WebUtil.getCurrentUserRole();
//        //数据权限（写sql需注意判断）
//        if (DataUtil.isNotEmpty(role)) {
//            params.put("role", role);
//        }
        if (DataUtil.isNotEmpty(params.get("pageNum"))) {
            current = Integer.valueOf(params.get("pageNum").toString());
        }
        if (DataUtil.isNotEmpty(params.get("pageSize"))) {
            size = Integer.valueOf(params.get("pageSize").toString());
        }
        if (DataUtil.isNotEmpty(params.get("orderBy"))) {
            orderBy = (String) params.get("orderBy");
            params.remove("orderBy");
        }
        if (size == -1) {
            return new Page<Long>();
        }
        Page<Long> page = new Page<Long>(current, size, orderBy);
        page.setAsc(false);
        return page;
    }

    /**
     * 根据Id查询(默认类型T)
     */
    public Page<T> getPage(Page<Long> ids) {
        if (ids != null) {
            Page<T> page = new Page<T>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<T> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                records.add(this.queryById(id));
            }
            page.setRecords(records);
            return page;
        }
        return new Page<T>();
    }

    /**
     * 根据Id查询(默认类型T)
     */
    public Page<Map<String, Object>> getPageMap(Page<Long> ids) {
        if (ids != null) {
            Page<Map<String, Object>> page = new Page<Map<String, Object>>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<Map<String, Object>> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                records.add(InstanceUtil.transBean2Map(this.queryById(id)));
            }
            page.setRecords(records);
            return page;
        }
        return new Page<Map<String, Object>>();
    }

    /**
     * 根据Id查询(cls返回类型Class)
     */
    public <K> Page<K> getPage(Page<Long> ids, Class<K> cls) {
        if (ids != null) {
            Page<K> page = new Page<K>(ids.getCurrent(), ids.getSize());
            page.setTotal(ids.getTotal());
            List<K> records = InstanceUtil.newArrayList();
            for (Long id : ids.getRecords()) {
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                records.add(k);
            }
            page.setRecords(records);
            return page;
        }
        return new Page<K>();
    }

    /**
     * 根据Id查询(默认类型T)
     */
    public List<T> getList(List<Long> ids) {
        List<T> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (Long id : ids) {
                list.add(this.queryById(id));
            }
        }
        return list;
    }

    /**
     * 根据Id查询(cls返回类型Class)
     */
    public <K> List<K> getList(List<Long> ids, Class<K> cls) {
        List<K> list = InstanceUtil.newArrayList();
        if (ids != null) {
            for (Long id : ids) {
                T t = this.queryById(id);
                K k = InstanceUtil.to(t, cls);
                list.add(k);
            }
        }
        return list;
    }

    @Transactional
    public void del(Long id, Long userId) {
        try {
            T record = this.queryById(id);
            record.setEnable(0);
            record.setUpdateTime(new Date());
            record.setUpdateBy(userId);
            mapper.updateById(record);
            CacheUtil.getCache().set(getCacheKey(id), record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            mapper.deleteById(id);
            CacheUtil.getCache().del(getCacheKey(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Transactional
    public Integer deleteByMap(Map<String, Object> columnMap) {
        return mapper.deleteByMap(columnMap);
    }

    @Transactional
    public Integer deleteByEntity(T t) {
        Wrapper<T> wrapper = new EntityWrapper<T>(t);
        return mapper.delete(wrapper);
    }

    /**
     * 保存数据
     *
     * @param record
     * @return
     */
    @Transactional
    public T save(T record) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Long userId = (Long) subject.getSession().getAttribute("userSessionId");
            record.setCreateBy(userId);
            record.setUpdateBy(userId);

            record.setUpdateTime(new Date());
//            if (record.getId() == null) {
            record.setCreateTime(new Date());
            record.setEnable(1);
            mapper.insert(record);
            /*else {
                String lockKey = getClass().getName() + record.getId();
				if (CacheUtil.getLock(lockKey)) {
					try {
						T org = queryById(record.getId());
						T update = InstanceUtil.getDiff(org, record);
						update.setId(record.getId());
						mapper.updateById(update);
					} finally {
						CacheUtil.unlock(lockKey);
					}
				}
			}*/
            record = mapper.selectById(record.getId());
            CacheUtil.getCache().set(getCacheKey(record.getId()), record);
        } catch (DuplicateKeyException e) {
            String msg = ExceptionUtil.getStackTraceAsString(e);
            logger.error(Constants.Exception_Head + msg, e);
            throw new RuntimeException("已经存在相同的配置.");
        } catch (Exception e) {
            String msg = ExceptionUtil.getStackTraceAsString(e);
            logger.error(Constants.Exception_Head + msg, e);
            throw new RuntimeException(msg);
        }
        return record;
    }


    /**
     * 更新方法(更新值变化的字段)
     * 注：updateAllField=true时，该方法只修改字段，字段置为空（null or ""）不会生效
     *
     * @param record         要更新的记录
     * @param updateAllField 是否更新所有字段（可以为空）
     * @return 更新后的记录
     */
    private T update(T record, Boolean updateAllField) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Long userId = (Long) subject.getSession().getAttribute("userSessionId");
            record.setUpdateBy(userId);

            record.setUpdateTime(new Date());
            if (record.getId() == null) {
                throw new RuntimeException("id不能为空.");
            }
            //String lockKey = getClass().getName() + record.getId();
            String lockKey = getLockKey("U" + record.getId());
            if (CacheUtil.getLock(lockKey)) {
                try {

                    /*T org = null;
                    String key = getCacheKey(record.getId());
                    try {
                        org = (T) CacheUtil.getCache().get(key);
                    } catch (Exception e) {
                        logger.error(Constants.Exception_Head, e);
                    }
                    if (org == null) {
                        org = mapper.selectById(record.getId());
                    }*/

                    if (updateAllField != null && updateAllField) {
                        mapper.updateAllColumnById(record);
                    } else {
                        T org = queryById(record.getId());
                        T update = InstanceUtil.getDiff(org, record);
                        update.setId(record.getId());
                        mapper.updateById(update);
                    }
                    record = mapper.selectById(record.getId());
                    try {
                        String key = getCacheKey(record.getId());
                        CacheUtil.getCache().set(key, record);
                    } catch (Exception e) {
                        logger.error(Constants.Exception_Head, e);
                    }
                } finally {
                    CacheUtil.unlock(lockKey);
                }
            } else {
                throw new RuntimeException("数据不一致!请刷新页面重新编辑!");
            }
            /*record = mapper.selectById(record.getId());
            CacheUtil.getCache().set(getCacheKey(record.getId()), record);*/
        } catch (DuplicateKeyException e) {
            String msg = ExceptionUtil.getStackTraceAsString(e);
            logger.error(Constants.Exception_Head + msg, e);
            throw new RuntimeException("已经存在相同的配置.");
        } catch (Exception e) {
            String msg = ExceptionUtil.getStackTraceAsString(e);
            logger.error(Constants.Exception_Head + msg, e);
            throw new RuntimeException(msg);
        }
        return record;
    }

    /**
     * 更新方法(更新所有的字段)
     *
     * @param record 要更新的记录
     * @return 更新后的记录
     */
    @Transactional
    public T updateAllField(T record) {
        return update(record, true);
    }

    /**
     * 更新方法(更新值变化的字段)
     * 注：字段置为空（null or ""）不会生效
     *
     * @param record 要更新的记录
     * @return 更新后的记录
     */
    @Transactional
    public T update(T record) {
        return update(record, false);
    }

    @SuppressWarnings("unchecked")
    public T queryById(Long id) {
        /*try {
            String key = getCacheKey(id);
            T record = (T) CacheUtil.getCache().get(key);
            if (record == null) {
                record = mapper.selectById(id);
                CacheUtil.getCache().set(key, record);
            }
            return record;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }*/
        return queryById(id, 1);
    }

    @SuppressWarnings("unchecked")
    private T queryById(Long id, int times) {
        String key = getCacheKey(id);
        T record = null;
        try {
            record = (T) CacheUtil.getCache().get(key);
        } catch (Exception e) {
            logger.error(Constants.Exception_Head, e);
        }
        if (record == null) {
            String lockKey = getLockKey(id);
            if (CacheUtil.getLock(lockKey)) {
                try {
                    record = mapper.selectById(id);
                    try {
                        CacheUtil.getCache().set(key, record);
                    } catch (Exception e) {
                        logger.error(Constants.Exception_Head, e);
                    }
                } finally {
                    CacheUtil.unlock(lockKey);
                }
            } else {
                if (times > 3) {
                    return mapper.selectById(id);
                }
                logger.debug(getClass().getSimpleName() + ":" + id + " retry queryById.");
                sleep(20);
                return queryById(id, times + 1);
            }
        }
        return record;
    }

    protected void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

    public T find(Long id) {
        return queryById(id);
    }

    public Page<T> query(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page);
    }

    public Page<Map<String, Object>> queryMap(Map<String, Object> params) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPageMap(page);
    }

    public List<T> queryList(Map<String, Object> params) {
        String role = WebUtil.getCurrentUserRole();
        //数据权限（写sql需注意判断）
        if (DataUtil.isNotEmpty(role)) {
            params.put("role", role);
        }
        List<Long> ids = mapper.selectIdPage(params);
        List<T> list = getList(ids);
        return list;
    }

    public List<T> findAll() {
        String role = WebUtil.getCurrentUserRole();
        //数据权限（写sql需注意判断）
        Map<String, Object> params = new HashMap<String, Object>();
        if (DataUtil.isNotEmpty(role)) {
            params.put("role", role);
        }
        List<Long> ids = mapper.selectIdPage(params);
        List<T> list = getList(ids);
        return list;
    }


    protected <P> Page<P> query(Map<String, Object> params, Class<P> cls) {
        Page<Long> page = getPage(params);
        page.setRecords(mapper.selectIdPage(page, params));
        return getPage(page, cls);
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }

    public List<T> selectList(Wrapper<T> entity) {
        return mapper.selectList(entity);
    }

    /**
     * 获取缓存键值
     */
    protected String getCacheKey(Object id) {
        String cacheName = getCacheKey();
        return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":").append(id).toString();
    }

    /**
     * 获取锁键值
     */
    protected String getLockKey(Object id) {
        String cacheName = getCacheKey();
        return new StringBuilder(Constants.CACHE_NAMESPACE).append(cacheName).append(":LOCK:").append(id).toString();
    }

    /**
     * 获取缓存键值
     *
     * @return
     */
    private String getCacheKey() {
        Class<?> cls = getClass();
        String cacheName = Constants.cacheKeyMap.get(cls);
        if (StringUtils.isBlank(cacheName)) {
            CacheConfig cacheConfig = cls.getAnnotation(CacheConfig.class);
            if (cacheConfig == null || cacheConfig.cacheNames() == null || cacheConfig.cacheNames().length < 1) {
                cacheName = getClass().getName();
            } else {
                cacheName = cacheConfig.cacheNames()[0];
            }
            Constants.cacheKeyMap.put(cls, cacheName);
        }
        return cacheName;
    }
}
