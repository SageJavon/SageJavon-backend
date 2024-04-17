package com.springboot.cli.common.exception;

/**
 * 异常解决方法接口
 *
 * @author fangkun-010
 */
public interface OpResolve {

    /**
     * 获取关于异常的解决方法提示
     *
     * @return String
     */
    String getTip();
}
