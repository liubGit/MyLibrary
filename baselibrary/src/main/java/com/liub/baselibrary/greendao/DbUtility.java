package com.liub.baselibrary.greendao;

import android.content.Context;

import com.liub.baselibrary.utils.Lmsg;
import com.liub.dao.bean.DaoMaster;
import com.liub.dao.bean.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.Arrays;
import java.util.List;

/**
 * Create by liub on 2019/4/18
 * Describe:数据库工具类
 */
public class DbUtility {
    private static String TAG="DbUtility";
    private static DaoSession daoSession;


    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static void init(Context context, int version) {
        ICardDbHelper helper = new ICardDbHelper(context, version);
        daoSession = new DaoMaster(helper.getWritableDb()).newSession();
    }

    /**
     * 更新实体
     *
     * @param abstractDao 数据库操作类
     * @param t           实体类
     * @param <T>         实体类型
     * @param <P>         主键类型
     */
    public synchronized static <T, P> void updateEntity(AbstractDao<T, P> abstractDao, T t) {
        if (abstractDao == null) {
            return;
        }
        if (t == null) {
            return;
        }
        try {
            abstractDao.update(t);
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库更新失败");
        }
    }

    /**
     * 保存实体
     *
     * @param abstractDao 数据库操作类
     * @param t           实体类
     * @param <T>         实体类型
     * @param <P>         主键类型
     */
    public synchronized static <T, P> void saveEntity(AbstractDao<T, P> abstractDao, T t) {
        if (abstractDao == null) {
            return;
        }
        if (t == null) {
            return;
        }
        try {
            abstractDao.insert(t);
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库插入失败");
        }
    }

    /**
     * 数据存在则替换，数据不存在则插入
     * @param abstractDao 数据库操作类
     * @param t           实体类
     * @param <T>         实体类型
     * @param <P>         主键类型
     */
    public synchronized static <T, P> void saveEntityReplace(AbstractDao<T, P> abstractDao, T t) {
        if (abstractDao == null) {
            return;
        }
        if (t == null) {
            return;
        }
        try {
            abstractDao.insertOrReplace(t);
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库插入失败");
        }
    }

    /**
     * 删除实体
     * @param abstractDao 数据库操作类
     * @param t           实体类
     * @param <T>         实体类型
     * @param <P>         主键类型
     */
    public static <T, P> void deleteEntity(AbstractDao<T, P> abstractDao, T t) {
        try {
            abstractDao.delete(t);
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库deleteEntity失败！");
        }
    }

    /**
     * 通过Key删除实体
     * @param abstractDao 数据库操作类
     * @param p           主键值
     * @param <T>         实体类型
     * @param <P>         主键类型
     */
    public static <T, P> void deleteEntityByKey(AbstractDao<T, P> abstractDao, P p) {
        try {
            abstractDao.deleteByKey(p);
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库deleteEntityByKey失败！");
        }
    }

    /**
     * 删除全部数据
     * @param abstractDao 数据库操作类
     * @param <T>         实体类型
     * @param <P>         主键类型
     */
    public static <T, P> void deleteAlls(AbstractDao<T, P> abstractDao) {
        try {
            abstractDao.deleteAll();
            abstractDao.detachAll();
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库deleteAll失败！");
        }
    }

    /**
     * 保存数据
     *
     * @param abstractDao 数据库操作类
     * @param t           实体类
     * @param <T>         实体类型
     * @param <P>         主键类型
     */
    public static <T, P> void saveInTx(AbstractDao<T, P> abstractDao, T... t) {
        saveInTx(abstractDao, Arrays.asList(t));
    }

    public static <T, P> void saveInTx(AbstractDao<T, P> abstractDao, List<T> t) {
        try {
            abstractDao.saveInTx(t);
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库saveInTx失败！");
        }
    }

    /**
     * 查询数据
     *
     * @param abstractDao 数据库操作类
     * @param <T>         实体类型
     * @param <P>         主键类型
     * @return 返回列表
     */
    public static <T, P> List<T> queryList(AbstractDao<T, P> abstractDao) {
        try {
            return abstractDao.queryBuilder().list();
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库queryList失败！");
        }
        return null;
    }

    public static <T, P> List<T> queryLimitList(AbstractDao<T, P> abstractDao, int limit) {
        try {
            return abstractDao.queryBuilder().limit(limit).list();
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库queryLimitList失败！");
        }
        return null;
    }

    public static <T, P> List<T> queryWhereList(AbstractDao<T, P> abstractDao, WhereCondition cond, WhereCondition... condMore) {
        try {
            return abstractDao.queryBuilder().where(cond, condMore).list();
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库queryWhereList失败！");
        }
        return null;
    }

    public static <T, P> List<T> queryOrderAscList(AbstractDao<T, P> abstractDao, Property... properties) {
        try {
            return abstractDao.queryBuilder().orderAsc(properties).list();
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库queryOrderAscList失败！");
        }
        return null;
    }

    public static <T, P> List<T> queryOrderDescList(AbstractDao<T, P> abstractDao, Property... properties) {
        try {
            return abstractDao.queryBuilder().orderDesc(properties).list();
        } catch (Exception e) {
            e.printStackTrace();
            Lmsg.e("TAG", "数据库queryOrderDescList失败！");
        }
        return null;
    }
}
