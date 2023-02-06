package com.smart.sso.server.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.sso.server.model.User;

/**
* @author:dongjing.chen
* @Description:
* @Company:ctg.cn
* @date:2022年11月29日
*/
@Repository
public interface UserMapper extends BaseMapper<User>{
    /**
     * 根据账号密码查询用户
     */
    @Select("SELECT * FROM sso_user WHERE login_name =#{loginName} AND password =#{password} AND deleted = 0")
    User getUserByNamePWD(@Param("loginName") String loginName, @Param("password") String password);
}
