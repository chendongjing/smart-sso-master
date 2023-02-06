# smart-sso
## 简述
    smart-sso使用当下最流行的SpringBoot技术，基于OAuth2认证授权协议，为您构建一个易理解、高可用、高扩展性的分布式单点登录应用基层。

## 相关文档
- [smart-sso：作者简介](https://blog.csdn.net/dongjing991?type=blog)

## 组织结构

```lua
smart-sso
├── smart-sso-client -- 客户端依赖包
├── smart-sso-client-redis -- 客户端依赖包，分布式redis支持
├── smart-sso-demo -- 客户端
├── smart-sso-server -- 服务端
```

## 单点登录原理
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201118170252707.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)


## 单点退出原理
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201118165835197.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)


## 效果展示
### 单点登录页
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201030163204421.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)

### 服务端登录成功页
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201030163112313.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)

### 客户端登录成功页
![](https://img-blog.csdnimg.cn/20201020163349855.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2E0NjYzNTA2NjU=,size_16,color_FFFFFF,t_70#pic_center)
