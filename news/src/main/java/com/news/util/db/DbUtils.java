package com.news.util.db;

/**
 * @author 谭志扬
 */
public class DbUtils {

    private DbUtils() {
    }

    /**
     * 获取sql中占位符的个数
     * @return 占位符个数
     */
    public static int getParaLength(String sql) {
        //存储占位符个数
//        int sqlLength = sql.length();
        int length = 0;
        //判断sql是否包含占位符
        if (!(sql.contains("?"))) {
            return length;
        }
        //存储查找开始位置
        int index = 0;
        //判断sql是否存在占位符
        do {
            //获取下一个占位符并替换字符串
            index = sql.indexOf("?", index)+1;
            //todo 判断是否被双引号或单引号包含
            length++;
        //从指定位置查找"?"不存在就退出循环
        } while (sql.indexOf("?", index) > -1);
        //返回占位符个数
        return length;
    }
}
