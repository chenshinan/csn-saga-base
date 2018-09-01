## saga-base

* spring Ioc容器的实现，从根源上是beanfactory，但真正可以作为一个可以独立使用的ioc容器还是DefaultListableBeanFactory，因此可以这么说，DefaultListableBeanFactory 是整个spring ioc的始祖。`ListableBeanFactory提供枚举所有的bean实例，而不是客户端通过名称一个一个的查询得出所有的实例`

* 如果想在生成对象时候完成某些初始化操作，而偏偏这些初始化操作又依赖于依赖注入，那么就无法在构造函数中实现。为此，可以使用@PostConstruct注解一个方法来完成初始化，@PostConstruct注解的方法将会在依赖注入完成后被自动调用。`Constructor >> @Autowired >> @PostConstruct`

* BeanPostProcessor是一个工厂钩子，允许Spring框架在新创建Bean实例时对其进行定制化修改。例如：`通过检查其标注的接口或者使用代理对其进行包裹`。应用上下文会从Bean定义中自动检测出BeanPostProcessor并将它们应用到随后创建的任何Bean上。`实现BeanPostProcessor接口可以对每一个要注入的bean进行前置处理和后置处理，若在该类注入的bean则提前注入`

* 通过`实现ApplicationContextAware`，重写setApplicationContext来获取Spring容器的上下文ApplicationContext，即可`获取到容器中的实例bean`

* `ReflectionUtils`反射工具包，可以反射出类的所有方法

* `AnnotationUtils`注解工具包，可以查找某个方法有没有某注解

* `TimeUnit`时间枚举包，可获取类型枚举、计算、延迟、转换时间等

* `ClassUtils`类工具包，可获取类名、包名、方法、与类有关的所有信息等

> TimeUnit.MILLISECONDS.toSeconds(30000)

* `@Retention注解`：定义被它所注解的注解保留多久

        SOURCE：被编译器忽略
        CLASS：注解将会被保留在Class文件中，但在运行时并不会被VM保留。这是默认行为，所有没有用Retention注解的注解，都会采用这种策略。
        RUNTIME：保留至运行时。所以我们可以通过反射去获取注解信息

* `ScheduledExecutorService`有时间计划的线程池：可延迟schedule、可循环（上次发起时间开始）scheduleAtFixedRate、可循环（上次结束时间开始）scheduleWithFixedDelay