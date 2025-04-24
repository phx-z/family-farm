# family-farm项目相关

## pom.xml
* spring-boot-dependencies与spring-boot-starter-parent：
    * 无需一起使用，一起使用时，需确保版本一致，避免依赖冲突。
    * 实际开发中应避免同时引入，因其功能重叠。
    * spring-boot-starter-parent简化配置流程。
    * spring-boot-dependencies灵活性强，适合已有企业级父 POM或,需独立管理构建流程的项目,需手动配置插件及构建参数（如 JDK 版本、编码)
* spring-boot-starter与spring-boot-starter-web：
    * 无需一起使用
    * spring-boot-starter-web内嵌 Tomcat、Spring MVC、JSON 支持。
    * spring-boot-starter-web的Maven 依赖树中已直接包含 spring-boot-starter，内嵌 Tomcat、Spring MVC、JSON 支持。
    * 常规 Web项目（REST API、MVC 架构）‌直接使用 spring-boot-starter-web，无需额外引入核心 Starter
    * 非 Web 项目‌（如纯后台服务、工具类项目，如批处理、CLI）使用 spring-boot-starter，并手动添加所需功能依赖（如数据库、消息队列）。
    * 无需 Web 功能（如纯后台服务、工具类项目）且需最小化依赖时，可单独使用核心 spring-boot-starter。但此类场景在实际开发中较为少见。
    * 通过 Spring Boot 父工程统一版本，避免手动协调依赖冲突。
      
