```
安装依赖  前提是已经安装mvn
mvn install
```

```
启动
mvn spring-boot:rum
```

```
浏览
打开localhost:8080
```

```
唯一登陆的账号密码 
admin admin
```

```
数据库名字 excel
表名字 swjl
```

```
测试用excel
appq.xlsx
注意 第一列第四列必然是 纯数字 其余必然要掺杂中文或字母
第一列必然是唯一的 因为是主键
```

```
数据库配置  在src/main/java/com/gonnect/upload/util/l.java中
```

```
建表
CREATE TABLE `excel`.`swjl` (
  `id` INT NOT NULL,
  `nsh` VARCHAR(100) NULL,
  `qymc` VARCHAR(100) NULL,
  `mse` INT NULL,
  `rtime` DATETIME NULL,
  PRIMARY KEY (`id`));
```