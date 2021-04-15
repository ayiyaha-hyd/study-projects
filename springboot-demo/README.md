# `Spring Boot`

---

依赖管理

父项目进行依赖管理，统一管理版本号，声明了常用的依赖版本，无需指定版本号，如需指定，则重写版本，手动指定版本号

```xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
```

父项目的父项目

```xml
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.4.4</version>
  </parent>
```

里边对版本及依赖进行了管理：

```xml
<properties>
    <activemq.version>5.16.1</activemq.version>
    <antlr2.version>2.7.7</antlr2.version>
    <appengine-sdk.version>1.9.87</appengine-sdk.version>
    <artemis.version>2.15.0</artemis.version>
    <aspectj.version>1.9.6</aspectj.version>
    <assertj.version>3.18.1</assertj.version>
    ...
  </properties>
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.apache.activemq</groupId>
        <artifactId>activemq-amqp</artifactId>
        <version>${activemq.version}</version>
      </dependency>
      <dependency>
        <groupId>org.apache.activemq</groupId>
        <artifactId>activemq-blueprint</artifactId>
        <version>${activemq.version}</version>
      </dependency>
        ...
    </dependencies>
  </dependencyManagement>
```



只要引入`spring-boot-starter-*`或者第三方的场景启动器，该场景所需要的依赖都会自动引入。



---

`Spring Boot`注解

```java
@SpringBootApplication//SpringBoot启动类

@Configuration//配置类等同于以前的配置文件
@Bean//给容器中添加组件，以方法名作为组件的id
//@Configuration中的@Bean获取的组件实例默认为为单实例，单例模式，配置类@Configuration本身也是组件，@Configuration(proxyBeanMethods = true)代理对象调用方法，默认为true，SpringBoot总会去检查容器中是否存在该组件，使其保持单例模式，单例模式解决容器中的组建依赖问题。false的意义在于SpringBoot不会去检查容器中是否已有该组件，每次调用都会重新创建，适合没有组件依赖的，效率高，@Import注解导入的类使用无参构造器生成，导入的组件名默认是全类名"com.xxx.xxx.."，容器中可以同时存在@Bean和@Import导入的两个类
@Component
@Controller
@Service
@Repository
@ComponentScan
@Import
@Conditional//条件装配
//    @ConditionalOnBean(name = {"pet01"})
//    @ConditionalOnBean(value = {Pet.class})
//    @ConditionalOnBean(type = {"com.hyd.demo.entity.Pet"})
//@Bean加载的类注意先后顺序，可能导致未加载成
@ImportResource//@ImportResource("classpath:xxx.xml")通过加载资源加载配置文件
@ConfigurationProperties//方法一：@Component+@ConfigurationProperties 方法二：在配置类上@EnableConfigurationProperties+@ConfigurationProperties
@PropertySource
```



主程序所在的包以及其下面所有的包都能扫描进去，想要改变包扫描路径可以使用`@scanBasePackages`注解或者`@ComponentScan`

`SpringBootApplication`等同于`@SpringBootConfiguration`,`@EnableAutoConfiguration`,`@ComponentScan("com.example.project")`三个注解







---

自动配置原理：



`AnnotationMetadata metadata`注解原信息标注在主配置类上



`Spring Boot`先加载所有的自动配置类 `xxAutoConfiguration`

每个自动配置类按照条件生效，默认绑定配置文件里的值

---

`Spring`原理

`SpringMVC`原理

自动配置原理

`SpringBoot`原理



主配置类中执行

```java
SpringApplication.run(SpringbootDemoApplication.class, args);
```

执行

```java
	public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
		return run(new Class<?>[] { primarySource }, args);
	}
```

执行`new SpringApplication(primarySources).run(args)`。

包含两个过程：

1.创建`SpringApplication`，

​	保存了一些信息。

2.运行`SpringApplication`。

```java
	public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        //创建应用，并运行
		return new SpringApplication(primarySources).run(args);
	}
```



创建`SpringApplication`，

其中`new SpringApplication(primarySources)`创建`SpringApplication`过程中，做了如下操作：



```java
	public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        //保存了资源加载器
		this.resourceLoader = resourceLoader;
        //primarySources=com.hyd.demo.SpringbootDemoApplication为主配置类
		Assert.notNull(primarySources, "PrimarySources must not be null");
        //保存了主配置类信息
		this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
        //设置了Web应用类型
		this.webApplicationType = WebApplicationType.deduceFromClasspath();
        //初始启动引导器
		this.bootstrappers = new ArrayList<>(getSpringFactoriesInstances(Bootstrapper.class));
        //设置初始化器
		setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
        //设置监听器
		setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
        //设置主程序
		this.mainApplicationClass = deduceMainApplicationClass();
	}
```



其中启动引导器会做如下操作：获取类加载器，通过`SpringFactoriesLoader.loadFactoryNames(type, classLoader)`，从`META-INF/spring.factories`配置文件下，获取`org.springframework.boot.Bootstrapper`配置的启动器

```java
	private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
        //获取类加载器
		ClassLoader classLoader = getClassLoader();
		// Use names and ensure unique to protect against duplicates
        //获取names
		Set<String> names = new LinkedHashSet<>(SpringFactoriesLoader.loadFactoryNames(type, classLoader));
        //创建实例
		List<T> instances = createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names);
		AnnotationAwareOrderComparator.sort(instances);
        //返回
		return instances;
	}
```



设置初始化器`setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));`，调用`getSpringFactoriesInstances`方法，从`META-INF/spring.factories`配置文件下，找`org.springframework.context.ApplicationContextInitializer`配置的初始化器

最终设置初始化器

```java
	public void setInitializers(Collection<? extends ApplicationContextInitializer<?>> initializers) {
		this.initializers = new ArrayList<>(initializers);
	}
```



接下来找应用监听器，调用`getSpringFactoriesInstances`方法获取,找`org.springframework.context.ApplicationListener`配置的监听器，返回实例



最后设置主程序`this.mainApplicationClass = deduceMainApplicationClass();`，从堆栈中找到方法名为`main`的类

```java
	private Class<?> deduceMainApplicationClass() {
		try {
			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
			for (StackTraceElement stackTraceElement : stackTrace) {
				if ("main".equals(stackTraceElement.getMethodName())) {
					return Class.forName(stackTraceElement.getClassName());
				}
			}
		}
		catch (ClassNotFoundException ex) {
			// Swallow and continue
		}
		return null;
	}
```





到此，创建`SpringApplication`结束，接下来就是运行`SpringApplication`

运行`SpringApplication`

```java
	public ConfigurableApplicationContext run(String... args) {
        //java计时器，记录引用的启动时间
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
        //创建引导上下文
		DefaultBootstrapContext bootstrapContext = createBootstrapContext();
		ConfigurableApplicationContext context = null;
        //设置headless模式
		configureHeadlessProperty();
        //获取应用运行监听器
		SpringApplicationRunListeners listeners = getRunListeners(args);
        //执行所有应用运行监听器starting方法
		listeners.starting(bootstrapContext, this.mainApplicationClass);
		try {
            //命令行参数
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
            //环境信息
			ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
            //创建IOC容器
			context = createApplicationContext();
            //为IOC容器设置applicationStartup
			context.setApplicationStartup(this.applicationStartup);
			prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
```



`stopWatch`记录应用启动时间，用于调试等

创建引导上下文(`Context`环境)：`DefaultBootstrapContext bootstrapContext = createBootstrapContext();`，获取之前保存的`bootstrappers`引导启动器，遍历执行启动器的`initialize`方法，完成对引导启动器的上下文环境设置

```java
	private DefaultBootstrapContext createBootstrapContext() {
		DefaultBootstrapContext bootstrapContext = new DefaultBootstrapContext();
		this.bootstrappers.forEach((initializer) -> initializer.initialize(bootstrapContext));
		return bootstrapContext;
	}
```



设置`headless`模式。

1. 什么是 java.awt.headless？
Headless模式是系统的一种配置模式。在系统可能缺少显示设备、键盘或鼠标这些外设的情况下可以使用该模式。


2. 何时使用和headless mode？
Headless模式虽然不是我们愿意见到的，但事实上我们却常常需要在该模式下工作，尤其是服务器端程序开发者。因为服务器（如提供Web服务的主机）往往可能缺少前述设备，但又需要使用他们提供的功能，生成相应的数据，以提供给客户端（如浏览器所在的配有相关的显示设备、键盘和鼠标的主机）。

 

3. 如何使用和Headless mode？
一般是在程序开始激活headless模式，告诉程序，现在你要工作在Headless mode下，就不要指望硬件帮忙了，你得自力更生，依靠系统的计算能力模拟出这些特性来:

```java
	private void configureHeadlessProperty() {
		System.setProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS,
				System.getProperty(SYSTEM_PROPERTY_JAVA_AWT_HEADLESS, Boolean.toString(this.headless)));
	}
```



从配置文件中读取配置`org.springframework.boot.context.event.EventPublishingRunListener`，实例化应用运行监听器，为接下来进行事件感知做准备

```java
SpringApplicationRunListeners listeners = getRunListeners(args);
```



```java
	private SpringApplicationRunListeners getRunListeners(String[] args) {
		Class<?>[] types = new Class<?>[] { SpringApplication.class, String[].class };
		return new SpringApplicationRunListeners(logger,
				getSpringFactoriesInstances(SpringApplicationRunListener.class, types, this, args),
				this.applicationStartup);
	}
```



所有应用运行监听器执行starting方法，`listeners.starting(bootstrapContext, this.mainApplicationClass);`，通知系统正在启动。



应用运行监听器有如下几个方法：

```java
public interface SpringApplicationRunListener {
	default void starting(ConfigurableBootstrapContext bootstrapContext) {
		starting();}
	default void starting() {}
	default void environmentPrepared(ConfigurableBootstrapContext bootstrapContext,ConfigurableEnvironment environment) {environmentPrepared(environment);}
	default void environmentPrepared(ConfigurableEnvironment environment) {}
	default void contextPrepared(ConfigurableApplicationContext context) {}
	default void contextLoaded(ConfigurableApplicationContext context) {}
	default void started(ConfigurableApplicationContext context) {}
	default void running(ConfigurableApplicationContext context) {}
	default void failed(ConfigurableApplicationContext context, Throwable exception) {}

}
```



保存命令行参数`ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);`



准备环境信息`ConfigurableEnvironment environment = prepareEnvironment(listeners, bootstrapContext, applicationArguments);`



```java
	private ConfigurableEnvironment prepareEnvironment(SpringApplicationRunListeners listeners,
			DefaultBootstrapContext bootstrapContext, ApplicationArguments applicationArguments) {
		// Create and configure the environment
        //获取环境信息
		ConfigurableEnvironment environment = getOrCreateEnvironment();
        //配置环境信息
		configureEnvironment(environment, applicationArguments.getSourceArgs());
        //绑定环境信息
		ConfigurationPropertySources.attach(environment);
        //调用监听器，通知环境准备已完成
		listeners.environmentPrepared(bootstrapContext, environment);
		DefaultPropertiesPropertySource.moveToEnd(environment);
		configureAdditionalProfiles(environment);
        //将环境信息绑定到SpringBoot应用中去
		bindToSpringApplication(environment);
		if (!this.isCustomEnvironment) {
			environment = new EnvironmentConverter(getClassLoader()).convertEnvironmentIfNecessary(environment,
					deduceEnvironmentClass());
		}
		ConfigurationPropertySources.attach(environment);
		return environment;
	}
```





配置环境信息`configureEnvironment(environment, applicationArguments.getSourceArgs());`

```java
	protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
		if (this.addConversionService) {
			ConversionService conversionService = ApplicationConversionService.getSharedInstance();
			environment.setConversionService((ConfigurableConversionService) conversionService);
		}
		configurePropertySources(environment, args);
		configureProfiles(environment, args);
	}
```



在环境信息中配置类型转换器`environment.setConversionService((ConfigurableConversionService) conversionService);`

读取所有配置源的配置属性值`configurePropertySources(environment, args);`

```java
	protected void configurePropertySources(ConfigurableEnvironment environment, String[] args) {
        //获取所有配置源
		MutablePropertySources sources = environment.getPropertySources();
		if (!CollectionUtils.isEmpty(this.defaultProperties)) {
			DefaultPropertiesPropertySource.addOrMerge(this.defaultProperties, sources);
		}
		if (this.addCommandLineProperties && args.length > 0) {
			String name = CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME;
			if (sources.contains(name)) {
				PropertySource<?> source = sources.get(name);
				CompositePropertySource composite = new CompositePropertySource(name);
				composite.addPropertySource(
						new SimpleCommandLinePropertySource("springApplicationCommandLineArgs", args));
				composite.addPropertySource(source);
				sources.replace(name, composite);
			}
			else {
				sources.addFirst(new SimpleCommandLinePropertySource(args));
			}
		}
	}
```



`configureProfiles(environment, args);`

配置该配置文件环境中哪些配置文件处于活动状态（默认情况下处于活动状态）。 其他配置文件可以在配置文件处理期间通过spring.profiles.active属性激活。



绑定环境信息`ConfigurationPropertySources.attach(environment);`



创建`IOC`容器`context = createApplicationContext();`

```java
	protected ConfigurableApplicationContext createApplicationContext() {
		return this.applicationContextFactory.create(this.webApplicationType);
	}
```

执行以下方法，根据`webApplicationType` (web应用类型)，创建IOC容器

```java
	ApplicationContextFactory DEFAULT = (webApplicationType) -> {
		try {
			switch (webApplicationType) {
			case SERVLET:
				return new AnnotationConfigServletWebServerApplicationContext();
			case REACTIVE:
				return new AnnotationConfigReactiveWebServerApplicationContext();
			default:
				return new AnnotationConfigApplicationContext();
			}
		}
		catch (Exception ex) {
			throw new IllegalStateException("Unable create a default ApplicationContext instance, "
					+ "you may need a custom ApplicationContextFactory", ex);
		}
	};
```





为IOC容器设置`applicationStartup`
`context.setApplicationStartup(this.applicationStartup);`



准备IOC容器上下文`prepareContext(bootstrapContext, context, environment, listeners, applicationArguments, printedBanner);`



```java
	private void prepareContext(DefaultBootstrapContext bootstrapContext, ConfigurableApplicationContext context,
			ConfigurableEnvironment environment, SpringApplicationRunListeners listeners,
			ApplicationArguments applicationArguments, Banner printedBanner) {
        //设置容器环境
		context.setEnvironment(environment);
        //执行容器的后置处理
		postProcessApplicationContext(context);
        //应用初始化器
		applyInitializers(context);
        //向各个监听器发送容器已经准备好的事件
		listeners.contextPrepared(context);
        //关闭bootstrapContext
		bootstrapContext.close(context);
		if (this.logStartupInfo) {
			logStartupInfo(context.getParent() == null);
			logStartupProfileInfo(context);
		}
		// Add boot specific singleton beans
        //获取实例工厂
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		beanFactory.registerSingleton("springApplicationArguments", applicationArguments);
		if (printedBanner != null) {
			beanFactory.registerSingleton("springBootBanner", printedBanner);
		}
		if (beanFactory instanceof DefaultListableBeanFactory) {
			((DefaultListableBeanFactory) beanFactory)
					.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
		}
		if (this.lazyInitialization) {
			context.addBeanFactoryPostProcessor(new LazyInitializationBeanFactoryPostProcessor());
		}
		// Load the sources
		Set<Object> sources = getAllSources();
		Assert.notEmpty(sources, "Sources must not be empty");
        //将bean加载到IOC容器(应用程序上下文)中
		load(context, sources.toArray(new Object[0]));
		listeners.contextLoaded(context);
	}
```



设置容器环境`context.setEnvironment(environment);`

执行容器的后置处理`postProcessApplicationContext(context);`

`applyInitializers(context);`应用初始化器，调用初始化方法`initializer.initialize(context);`，

```java
	protected void applyInitializers(ConfigurableApplicationContext context) {
		for (ApplicationContextInitializer initializer : getInitializers()) {
			Class<?> requiredType = GenericTypeResolver.resolveTypeArgument(initializer.getClass(),
					ApplicationContextInitializer.class);
			Assert.isInstanceOf(requiredType, context, "Unable to call initializer.");
			initializer.initialize(context);
		}
	}
```



向各个监听器发送容器已经准备好的事件`listeners.contextPrepared(context);`，各个监听器执行方法

```java
	void contextPrepared(ConfigurableApplicationContext context) {
		doWithListeners("spring.boot.application.context-prepared", (listener) -> listener.contextPrepared(context));
	}
```



关闭bootstrapContext

获取实例工厂

将main函数中的args参数封装成单例Bean，注册进容器`beanFactory.registerSingleton("springApplicationArguments", applicationArguments);`



将 printedBanner 也封装成单例，注册进容器`beanFactory.registerSingleton("springBootBanner", printedBanner);`

`Set<Object> sources = getAllSources();`拿到启动类`com.hyd.demo.SpringbootDemoApplication`

加载`load(context, sources.toArray(new Object[0]));`

将bean加载到应用程序上下文中

```java
	protected void load(ApplicationContext context, Object[] sources) {
		if (logger.isDebugEnabled()) {
			logger.debug("Loading source " + StringUtils.arrayToCommaDelimitedString(sources));
		}
        //创建BeanDefinitionLoader
		BeanDefinitionLoader loader = createBeanDefinitionLoader(getBeanDefinitionRegistry(context), sources);
		if (this.beanNameGenerator != null) {
			loader.setBeanNameGenerator(this.beanNameGenerator);
		}
		if (this.resourceLoader != null) {
			loader.setResourceLoader(this.resourceLoader);
		}
		if (this.environment != null) {
			loader.setEnvironment(this.environment);
		}
        //注册Bean到容器中
		loader.load();
	}
```



创建BeanDefinitionLoader：`BeanDefinitionLoader loader = createBeanDefinitionLoader(getBeanDefinitionRegistry(context), sources);`

```java
	protected BeanDefinitionLoader createBeanDefinitionLoader(BeanDefinitionRegistry registry, Object[] sources) {
		return new BeanDefinitionLoader(registry, sources);
	}

```



BeanDefinitionRegistry定义了很重要的方法registerBeanDefinition()，该方法将BeanDefinition注册进DefaultListableBeanFactory容器的beanDefinitionMap中。

```java
	BeanDefinitionLoader(BeanDefinitionRegistry registry, Object... sources) {
		Assert.notNull(registry, "Registry must not be null");
		Assert.notEmpty(sources, "Sources must not be empty");
		this.sources = sources;
        //注解形式的Bean定义读取器 比如：@Configuration @Bean @Component @Controller @Service等等
		this.annotatedReader = new AnnotatedBeanDefinitionReader(registry);
        //XML形式的Bean定义读取器
		this.xmlReader = (XML_ENABLED ? new XmlBeanDefinitionReader(registry) : null);
        //Groovy 读取器
		this.groovyReader = (isGroovyPresent() ? new GroovyBeanDefinitionReader(registry) : null);
        //类路径扫描器
		this.scanner = new ClassPathBeanDefinitionScanner(registry);
        //扫描器添加排除过滤器
		this.scanner.addExcludeFilter(new ClassExcludeFilter(sources));
	}
```







注册Bean到容器中：`loader.load();`

```java
	void load() {
		for (Object source : this.sources) {
			load(source);
		}
	}
```



```java
	private void load(Object source) {
		Assert.notNull(source, "Source must not be null");
		if (source instanceof Class<?>) {
			load((Class<?>) source);
			return;
		}
		if (source instanceof Resource) {
			load((Resource) source);
			return;
		}
		if (source instanceof Package) {
			load((Package) source);
			return;
		}
		if (source instanceof CharSequence) {
			load((CharSequence) source);
			return;
		}
		throw new IllegalArgumentException("Invalid source type " + source.getClass());
	}
```



四种加载方式：

```java
	private void load(Class<?> source) {
		if (isGroovyPresent() && GroovyBeanDefinitionSource.class.isAssignableFrom(source)) {
			// Any GroovyLoaders added in beans{} DSL can contribute beans here
			GroovyBeanDefinitionSource loader = BeanUtils.instantiateClass(source, GroovyBeanDefinitionSource.class);
			((GroovyBeanDefinitionReader) this.groovyReader).beans(loader.getBeans());
		}
        //检查Bean是否符合注册条件
		if (isEligible(source)) {
			this.annotatedReader.register(source);
		}
	}

	private void load(Resource source) {
		if (source.getFilename().endsWith(".groovy")) {
			if (this.groovyReader == null) {
				throw new BeanDefinitionStoreException("Cannot load Groovy beans without Groovy on classpath");
			}
			this.groovyReader.loadBeanDefinitions(source);
		}
		else {
			if (this.xmlReader == null) {
				throw new BeanDefinitionStoreException("Cannot load XML bean definitions when XML support is disabled");
			}
			this.xmlReader.loadBeanDefinitions(source);
		}
	}

	private void load(Package source) {
		this.scanner.scan(source.getName());
	}

	private void load(CharSequence source) {
		String resolvedSource = this.scanner.getEnvironment().resolvePlaceholders(source.toString());
		// Attempt as a Class
		try {
			load(ClassUtils.forName(resolvedSource, null));
			return;
		}
		catch (IllegalArgumentException | ClassNotFoundException ex) {
			// swallow exception and continue
		}
		// Attempt as Resources
		if (loadAsResources(resolvedSource)) {
			return;
		}
		// Attempt as package
		Package packageResource = findPackage(resolvedSource);
		if (packageResource != null) {
			load(packageResource);
			return;
		}
		throw new IllegalArgumentException("Invalid source '" + resolvedSource + "'");
	}
```



注册一个或多个要处理的组件类。
调用register是幂等的； 多次添加同一组件类不会产生任何其他影响

```java
	public void register(Class<?>... componentClasses) {
		for (Class<?> componentClass : componentClasses) {
			registerBean(componentClass);
		}
	}
```



从给定的bean类中注册一个bean，并从类声明的批注中派生其元数据

```java
	public void registerBean(Class<?> beanClass) {
		doRegisterBean(beanClass, null, null, null, null);
	}
```

从给定的bean类中注册一个bean，并从类声明的批注中派生其元数据。

```java
	private <T> void doRegisterBean(Class<T> beanClass, @Nullable String name,
			@Nullable Class<? extends Annotation>[] qualifiers, @Nullable Supplier<T> supplier,
			@Nullable BeanDefinitionCustomizer[] customizers) {

		AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
		if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
			return;
		}

		abd.setInstanceSupplier(supplier);
		ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
		abd.setScope(scopeMetadata.getScopeName());
		String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));

		AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
		if (qualifiers != null) {
			for (Class<? extends Annotation> qualifier : qualifiers) {
				if (Primary.class == qualifier) {
					abd.setPrimary(true);
				}
				else if (Lazy.class == qualifier) {
					abd.setLazyInit(true);
				}
				else {
					abd.addQualifier(new AutowireCandidateQualifier(qualifier));
				}
			}
		}
		if (customizers != null) {
			for (BeanDefinitionCustomizer customizer : customizers) {
				customizer.customize(abd);
			}
		}

		BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
		definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
		BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
	}
```



















发布容器已加载事件`listeners.contextLoaded(context);`

