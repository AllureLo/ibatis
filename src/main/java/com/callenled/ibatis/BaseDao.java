package com.callenled.ibatis;

import com.callenled.ibatis.annotation.ColumnList;
import com.callenled.ibatis.annotation.ColumnName;
import com.callenled.ibatis.annotation.TableName;
import com.callenled.mapper.BaseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: callenled
 * @Date: 19-8-16 上午10:57
 */
@Component
public class BaseDao<T extends BaseModel> {

    private final BaseMapper baseMapper;

    @Autowired
    public BaseDao(BaseMapper baseMapper) {
        this.baseMapper = baseMapper;
    }

    /**
     * 当id为空的时候add记录 当id不为空的时候update
     *
     * @param object  待保存的实体
     * @param clazz   待保存的实体类型
     */
    public void save(T object, Class<T> clazz) {
        if (null == object) {
            throw new RuntimeException("Entity can't be null.");
        }
        if (object.getId() == null) {
            add(object, clazz);
        } else {
            update(object, clazz);
        }
    }

    /**
     * 传入对象,保存进数据库 对象和表名的关系ClothFlaw 对应 表 clothFlaw 第一个字母变小写,如果表名不同,需要指定表名
     * 插入的列名和要对象的属性名称一样
     *
     * @param object  待添加的实体
     * @param clazz   待添加的实体类型
     */
    public void add(T object, Class<T> clazz) {
        if (null == object) {
            throw new RuntimeException("Entity can't be null.");
        }

        Map<String, Object> para = new HashMap<>(16);

        // 数据库表名
        para.put("tableName", getTableName(clazz));

        // 设置初始化
        Date date = new Date();
        object.setCreateTime(date);
        object.setUpdateTime(date);
        object.setIsDelete(0);

        /* 处理entity每个字段 */
        List<Field> fields = getDeclaredFields(clazz);

        List<Property> pros = new ArrayList<>();
        para.put("properties", pros);

        Property property;

        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // 获取字段对应的value
            String name = getColumnName(field);

            if ("id".equalsIgnoreCase(name) || "start".equalsIgnoreCase(name) || "limit".equalsIgnoreCase(name)) {
                continue;
            }
            
            try {
                // 取消属性的访问权限控制，即使private属性也可以进行访问。
                field.setAccessible(true);
                // 调用get()方法取得对应属性值。
                Object value = field.get(object);
                // value为空或者不是基础数据
                if (value == null || isNotBasicType(value)) {
                    continue;
                }

                property = new Property(name, value);
                pros.add(property);
            } catch (Exception ignored) {
                // 跳过没有get的属性
            }
        }

        // 调用mybatis
        baseMapper.add(para);

        // 设置回Id
        object.setId(Long.parseLong(para.get("id").toString()));
    }

    /**
     * 批量插入
     *
     * @param oList  待保存的实体列表
     * @param clazz  待保存的实体类型
     */
    public void addList(List<T> oList, Class<T> clazz) {
        if (null == oList || 0 == oList.size()) {
            throw new RuntimeException("List can't be blank.");
        }

        Map<String, Object> para = new HashMap<>(16);
        
        // 数据库表名
        para.put("tableName", getTableName(clazz));

        List<String> colName = new ArrayList<>();
        para.put("colName", colName);
        
        // 遍历字段名
        List<Field> fields = getDeclaredFields(clazz);

        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // 获取字段名
            String name = getColumnName(field);

            if ("id".equalsIgnoreCase(name) || "start".equalsIgnoreCase(name) || "limit".equalsIgnoreCase(name)) {
                continue;
            }
            colName.add(name);
        }

        List<List<Object>> valueList = new ArrayList<>();
        para.put("valueList", valueList);
        // 根据字段名 查询字段的值
        List<Object> values = new ArrayList<>();
        // 对list循环
        for (T object : oList) {
            // 设置初始化
            Date date = new Date();
            object.setCreateTime(date);
            object.setUpdateTime(date);
            object.setIsDelete(0);
            // 对list内 字段循环
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())) {
                    continue;
                }
                //获取对应的value
                String name = getColumnName(field);

                if ("id".equalsIgnoreCase(name) || "start".equalsIgnoreCase(name) || "limit".equalsIgnoreCase(name)) {
                    continue;
                }
                try {
                    // 取消属性的访问权限控制，即使private属性也可以进行访问。
                    field.setAccessible(true);
                    // 调用get()方法取得对应属性值。
                    Object value = field.get(object);

                    values.add(value);
                } catch (Exception ignored) {
                    // 跳过没有get的属性
                }
            }
            valueList.add(values);
        }

        baseMapper.addList(para);
    }

    /**
     * 通用的更新 对象和表名的关系ClothFlaw 对应 表 clothFlaw 第一个字母变小写,如果表名不同,需要指定表名
     * 更新的列名和要对象的属性名称一样
     *
     * @param object  待更新的实体
     * @param clazz   待更新的实体类型
     */
    public void update(T object, Class<T> clazz) {
        if (object == null || object.getId() == null) {
            throw new RuntimeException("Entity or idName can't be null or blank.");
        }

        Map<String, Object> para = new HashMap<>(16);
        para.put("idName", "id");
        para.put("idValue", object.getId());

        // 数据库表名
        para.put("tableName", getTableName(clazz));

        // 设置修改时间
        object.setUpdateTime(new Date());

        List<Field> fields = getDeclaredFields(clazz);

        List<Property> pros = new ArrayList<>();
        para.put("properties", pros);

        Property property;

        // 设置更新字段
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            // 获取字段对应的value
            String name = getColumnName(field);

            try {
                // 取消属性的访问权限控制，即使private属性也可以进行访问。
                field.setAccessible(true);

                if ("id".equalsIgnoreCase(name)) {
                    continue;
                }

                // 调用get()方法取得对应属性值。
                Object value = field.get(object);

                if (value == null || isNotBasicType(value)) {
                    continue;
                }

                property = new Property(name, value);
                pros.add(property);
            } catch (Exception e) {
                // 跳过没有get的属性
            }

        }

        baseMapper.update(para);
    }

    /**
     * 根据id查询单条记录
     *
     * @param value    要查询的主键值
     * @param clazz    待查询的实体类型
     * @return
     */
    public T quickQueryOneById(Object value, Class<T> clazz) {
        return quickQueryOne("id", value, clazz);
    }

    /**
     * 快速查询只有一个查询参数的对象
     *
     * @param name  参数名
     * @param value 参数值
     * @param clazz 待查询的实体类型
     * @return 单条结果
     */
    public T quickQueryOne(String name, Object value, Class<T> clazz) {
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("Field name can't be blank.");
        }

        if (null == value) {
            throw new RuntimeException("Field value can't be null.");
        }

        if (!clazz.isAnnotationPresent(ColumnList.class)) {
            throw new RuntimeException("ColumnList can't be lack.");
        }

        Map<String, Object> para = new HashMap<>(8);
        // 数据库表名
        para.put("tableName", getTableName(clazz));

        // 数据库查询字段
        ColumnList columnList = clazz.getAnnotation(ColumnList.class);
        para.put("baseColumnList", columnList.value());

        // 查询参数的对象
        para.put("idName", name);
        para.put("idValue", value);

        Map<String, Object> map = baseMapper.queryOne(para);
        if (null == map) {
            return null;
        }
        return packResultMap(clazz, map);
    }

    /**
     * 通用的逻辑删除
     *
     * @param object  待删除的实体类
     * @param clazz   待删除的实体类型
     */
    public void deleteById(T object, Class<T> clazz) {
        if (object == null) {
            throw new RuntimeException("Entity can't be null.");
        }

        //逻辑改为删除状态
        object.setIsDelete(1);
        this.update(object, clazz);
    }

    /**
     * 根据id删除
     * 通用的逻辑删除
     *
     * @param value   待删除的主键值
     * @param clazz   待删除的实体类型
     */
    public void deleteById(Object value, Class<T> clazz) {
        delete("id", value, clazz);
    }

    /**
     * 通用的逻辑删除
     *
     * @param idName  属性名
     * @param idValue 属性值
     * @param clazz   待删除的实体类型
     */
    public void delete(String idName, Object idValue, Class<T> clazz) {
        if (idName == null || idValue == null) {
            throw new RuntimeException("idValue or idName can't be null or blank.");
        }

        Map<String, Object> para = new HashMap<>(16);
        para.put("idName", idName);
        para.put("idValue", idValue);

        // 数据库表名
        para.put("tableName", getTableName(clazz));

        List<Property> pros = new ArrayList<>();
        para.put("properties", pros);

        try {
            //修改时间
            Field field = BaseModel.class.getDeclaredField("updateTime");
            String name = getColumnName(field);
            Property property = new Property(name, new Date());
            pros.add(property);
            //删除状态
            field = BaseModel.class.getDeclaredField("isDelete");
            name = getColumnName(field);
            property = new Property(name, 1);
            pros.add(property);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        baseMapper.update(para);
    }

    /**
     * 根据id删除
     * 通用的物理删除
     *
     * @param value   待删除的主键值
     * @param clazz   待删除的实体类型
     */
    public void quickDeleteById(Object value, Class<T> clazz) {
        quickDelete("id", value, clazz);
    }

    /**
     * 通用的物理删除
     *
     * @param name  属性名
     * @param value 属性值
     * @param clazz 待删除的实体类型
     */
    public void quickDelete(String name, Object value, Class<T> clazz) {
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("Field name can't be blank.");
        }

        if (null == value) {
            throw new RuntimeException("Field value can't be null.");
        }

        Map<String, Object> para = new HashMap<>(8);
        // 数据库表名
        para.put("tableName", getTableName(clazz));
        para.put("idName", name);
        para.put("idValue", value);

        baseMapper.delete(para);
    }

    /**
     * 默认字段查询
     *
     * @return list
     */
    public List<T> query(T object, Integer start, Integer limit, Class<T> clazz) {
        return query(object, null, null, start, limit, clazz);
    }

    /**
     * 默认字段查询
     *
     * @return count
     */
    public int queryCount(T object, Class<T> clazz) {
        return queryCount(object, null, null, clazz);
    }

    /**
     * 使用like条件查询
     *
     * @return list
     */
    public List<T> queryUsingLike(T object, List<String> likeProperties, Integer start, Integer limit, Class<T> clazz) {
        return query(object, likeProperties, null, start, limit, clazz);
    }

    /**
     * 使用like条件查询
     *
     * @return count
     */
    public int queryCountUsingLike(T object, List<String> likeProperties, Class<T> clazz) {
        return queryCount(object, likeProperties, null, clazz);
    }

    /**
     * 使用自定义条件查询
     *
     * @return list
     */
    public List<T> queryCustomize(T object, List<String> userPara, Integer start, Integer limit, Class<T> clazz) {
        return query(object, null, userPara, start, limit, clazz);
    }

    /**
     * 使用自定义条件查询
     *
     * @return count
     */
    public int queryCountCustomize(T object, List<String> userPara, Class<T> clazz) {
        return queryCount(object, null, userPara, clazz);
    }

    /**
     * 通用的分页查询 根据object中的不为空的属性进行查询 对象和表名的关系ClothFlaw 对应 表 clothFlaw
     * 第一个字母变小写,如果表名不同,需要指定表名 支持like查询方式,需要like查询的属性以likeProperties字符串链表的方式传入,
     * 传入的属性要在object中存在 支持自定义sql片段查询.
     * List<String> userPara = new ArrayList<>();
     * userPara.add("and name like '%time%'");
     * <p>
     * 默认使用主键id 作为子查询
     *
     * @param object         等值查询条件
     * @param likeProperties 相似查询条件
     * @param start          页码          默认第一页
     * @param limit          每页数据量     默认每页10条数据
     * @param clazz          待查询的实体类型
     */
    public List<T> query(T object, List<String> likeProperties, List<String> userPara, Integer start, Integer limit, Class<T> clazz) {
        return query(object, likeProperties, userPara, "id", start, limit, clazz);
    }

    /**
     * 通用的分页查询 根据object中的不为空的属性进行查询 对象和表名的关系ClothFlaw 对应 表 clothFlaw
     * 第一个字母变小写,如果表名不同,需要指定表名 支持like查询方式,需要like查询的属性以likeProperties字符串链表的方式传入,
     * 传入的属性要在object中存在 支持自定义sql片段查询.
     * List<String> userPara = new ArrayList<>();
     * userPara.add("and name like '%time%'");
     * <p>
     * 带分页的查询 start,limit
     *
     * @param object         等值查询条件
     * @param likeProperties 相似查询条件
     * @param primaryKey     主键          一般为id
     * @param start          页码          默认第一页
     * @param limit          每页数据量     默认每页10条数据
     * @param clazz          待查询的实体类型
     */
    public List<T> query(T object, List<String> likeProperties, List<String> userPara, String primaryKey, Integer start, Integer limit, Class<T> clazz) {
        if (null == object) {
            throw new RuntimeException("Entity can't be null.");
        }

        if (null == primaryKey) {
            throw new RuntimeException("Primary key can't be null.");
        }

        if (!clazz.isAnnotationPresent(ColumnList.class)) {
            throw new RuntimeException("ColumnList can't be lack.");
        }

        if (null == start || start < 1) {
            start = 1;
        }
        if (null == limit || limit < 1) {
            limit = 10;
        }

        // 添加主键 作为子查询使用
        try {
            clazz.getDeclaredField(primaryKey);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("No such field: " + primaryKey + " in " + clazz.getName());
        }
        Map<String, Object> para = new HashMap<>(16);
        para.put("primaryKey", primaryKey);

        // 用户自己的sql语句片段
        para.put("userPara", userPara);
        para.put("offset", (start - 1) * limit);
        para.put("limit", limit);

        // 获取表名
        para.put("tableName", getTableName(clazz));

        // 数据库查询字段
        ColumnList columnList = clazz.getAnnotation(ColumnList.class);
        para.put("baseColumnList", columnList.value());

        // 普通属性
        List<Property> pros = new ArrayList<>();
        // like属性
        List<Property> likePros = new ArrayList<>();
        para.put("properties", pros);
        para.put("likeProperties", likePros);

        /* 对每个字段做处理 */
        packParasForQuery(object, likeProperties, likePros, pros, clazz);
        // 执行查询
        List<Map<String, Object>> result = baseMapper.query(para);

        // 查询结果封装为Object
        List<T> list = new ArrayList<>();
        for (Map<String, Object> map : result) {
            T t = packResultMap(clazz, map);
            list.add(t);
        }
        return list;
    }

    /**
     * 数据量大的时候 查询列表size 十分消耗性能
     * 所以分开实现 前端列表尽可能少调用该方法
     */
    public int queryCount(T object, List<String> likeProperties, List<String> userPara, Class<T> clazz) {
        if (null == object) {
            throw new RuntimeException("Entity can't be null.");
        }

        // 用户自己的sql语句片段
        Map<String, Object> para = new HashMap<>(16);
        para.put("userPara", userPara);

        // 获取表名
        para.put("tableName", getTableName(clazz));

        // 普通属性
        List<Property> pros = new ArrayList<>();
        // like属性
        List<Property> likePros = new ArrayList<>();
        para.put("properties", pros);
        para.put("likeProperties", likePros);

        /* 对每个字段做处理 */
        packParasForQuery(object, likeProperties, likePros, pros, clazz);

        return baseMapper.queryCount(para);
    }

    /**
     * query专用
     * 查询条件para 组装
     */
    private void packParasForQuery(T object, List<String> likeProperties, List<Property> likePros, List<Property> pros, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Property property;
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            String name = getColumnName(field);

            // 跳过id属性 主键查询应使用queryOne()方法
            if ("id".equalsIgnoreCase(name)) {
                continue;
            }

            try {
                // 因为mybatis映射Object里的list时用的是get方法，而如果get得到一个null会报空指针，所以全都返回一个空list
                // 这里会产生冲突，所以过滤掉list类型属性
                Type type = field.getType();
                if (StringUtils.equals(type.toString(), "interface java.util.List")) {
                    continue;
                }

                // 取消属性的访问权限控制，即使private属性也可以进行访问。
                field.setAccessible(true);
                // 调用get()方法取得对应属性值。
                Object value = field.get(object);

                if (value == null || isNotBasicType(value)) {
                    continue;
                }

                property = new Property(name, value);

                if (null == likeProperties) {
                    pros.add(property);
                } else {
                    if (likeProperties.contains(name)) {
                        likePros.add(property);
                    } else {
                        pros.add(property);
                    }
                }
            } catch (Exception e) {
                // 跳过没有get的属性
            }
        }
    }

    /**
     * query专用
     * 查询结果map 组装
     */
    private T packResultMap(Class<T> clazz, Map<String, Object> map) {
        T resultObject = null;
        try {
            resultObject = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        assert resultObject != null;
        List<Field> fields = getDeclaredFields(clazz);
        // 遍历所有字段
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }

            String name = getColumnName(field);

            // get(key)时间复杂度为o(1)
            Object value = map.get(name);
            if (value == null || isNotBasicType(value)) {
                continue;
            }

            try {
                // 取消属性的访问权限控制，即使private属性也可以进行访问。
                field.setAccessible(true);
                // 调用set()方法设置对应属性值。
                field.set(resultObject, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return resultObject;
    }

    /**
     * 根据class上的TableName注解获取表名
     * 若空则根据class名称生成表名,如 EntityExample.java 则生成 entityExample
     */
    private String getTableName(Class<T> clazz) {
        if (null == clazz) {
            throw new RuntimeException("Entity can't be null.");
        }

        if (clazz.isAnnotationPresent(TableName.class)) {
            TableName tableName = clazz.getAnnotation(TableName.class);
            return tableName.value();
        } else {
            String className = clazz.getName();
            String name = className.substring(className.lastIndexOf(".") + 1);
            char[] chs = name.toCharArray();
            if (chs.length > 0) {
                chs[0] = Character.toLowerCase(chs[0]);
            }
            return new String(chs);
        }
    }

    /**
     * 根据field上的FiledName注解获取字段名
     * 若空则根据field名称获取字段名
     */
    private String getColumnName(Field field) {
        if (null == field) {
            throw new RuntimeException("Field can't be null.");
        }
        if (field.isAnnotationPresent(ColumnName.class)) {
            ColumnName fieldName = field.getAnnotation(ColumnName.class);
            return fieldName.value();
        } else {
            return field.getName().toLowerCase();
        }
    }

    /**
     * 判断是否为基础类型
     */
    private boolean isNotBasicType(Object o) {
        return !(o instanceof String) && !(o instanceof Integer) && !(o instanceof Long) && !(o instanceof Date) && !(o instanceof Double) && !(o instanceof Enum) && !(o instanceof Boolean) && !(o instanceof BigDecimal);
    }

    /**
     * 获取自身以及父类的属性
     *
     * @param clazz   实体类型
     * @return
     */
    private List<Field> getDeclaredFields(Class<T> clazz){
        List<Field> fields = new ArrayList<>();
        for(Class<?> tClass = clazz; tClass != Object.class ; tClass = tClass.getSuperclass()) {
            try {
                Field[] arrField = tClass.getDeclaredFields();
                if(arrField.length > 0){
                    fields.addAll(Arrays.asList(arrField));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fields;
    }
}