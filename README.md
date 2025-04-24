# family-farm项目相关


## pom.xml
spring-boot-dependencies与spring-boot-starter-parent无需一起使用，一起使用，一起使用时，需确保版本一致，避免依赖冲突，但实际开发中应避免同时引入，因其功能重叠。spring-boot-dependencies灵活性强，适合已有企业级父 POM或,需独立管理构建流程的项目,需手动配置插件及构建参数（如 JDK 版本、编码)
