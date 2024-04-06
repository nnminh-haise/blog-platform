# Personal blog platform

> *This is a personal blog platform SSR WebApp using Spring and PostgreSQL.*

<div align="center">
  <img src="database-diagram.png" style="width:800px; background-color:white"/>
  <p align="center" style="font-style: italic; color: #999;">
    Personal blog platform's database diagram
  </p>
</div>

## Database scripts

### Create table script

```sql
CREATE DATABASE "blog-platform"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

COMMENT ON DATABASE "blog-platform"
    IS 'A database for storing blog of a personal blog platform';
```

### Blogs table

Script for sequence of `id`:

```sql
CREATE SEQUENCE IF NOT EXISTS public.blogs_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY blogs.id;

ALTER SEQUENCE public.blogs_id_seq
    OWNER TO postgres;
```

Script for table:

```sql
CREATE TABLE IF NOT EXISTS public.blogs
(
    id integer NOT NULL DEFAULT nextval('blogs_id_seq'::regclass),
    title text COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default" NOT NULL,
    attachment text COLLATE pg_catalog."default" NOT NULL,
    slug text COLLATE pg_catalog."default" NOT NULL,
    hidden_status boolean DEFAULT false,
    create_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone NOT NULL,
    delete_at timestamp without time zone,
    publish_at timestamp without time zone,
    CONSTRAINT blogs_pkey PRIMARY KEY (id),
    CONSTRAINT "unique blog title" UNIQUE (title)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.blogs
    OWNER to postgres;
```

### Categories table

Script for sequence of `id`:

```sql
CREATE SEQUENCE IF NOT EXISTS public.categories_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY categories.id;

ALTER SEQUENCE public.categories_id_seq
    OWNER TO postgres;
```

Script for table:

```sql
CREATE TABLE IF NOT EXISTS public.categories
(
    id integer NOT NULL DEFAULT nextval('categories_id_seq'::regclass),
    name text COLLATE pg_catalog."default" NOT NULL,
    create_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone NOT NULL,
    delete_at timestamp without time zone,
    CONSTRAINT categories_pkey PRIMARY KEY (id),
    CONSTRAINT "Unique category name" UNIQUE (name)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.categories
    OWNER to postgres;
```

### Category_details table

Script for sequence of `category_id`:

```sql
CREATE SEQUENCE IF NOT EXISTS public.category_details_category_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY category_details.category_id;

ALTER SEQUENCE public.category_details_category_id_seq
    OWNER TO postgres;
```

Script for sequence of `blog_id`:

```sql
CREATE SEQUENCE IF NOT EXISTS public.category_details_blog_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1
    OWNED BY category_details.blog_id;

ALTER SEQUENCE public.category_details_blog_id_seq
    OWNER TO postgres;
```

Script for table:

```sql
CREATE TABLE IF NOT EXISTS public.category_details
(
    id uuid DEFAULT uuid_generate_v4(),
    category_id integer NOT NULL DEFAULT nextval('category_details_category_id_seq'::regclass),
    blog_id integer NOT NULL DEFAULT nextval('category_details_blog_id_seq'::regclass),
    create_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone NOT NULL,
    delete_at timestamp without time zone,
    CONSTRAINT "Category Detail FK category" FOREIGN KEY (category_id)
        REFERENCES public.categories (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT "Category Detail FL blog" FOREIGN KEY (blog_id)
        REFERENCES public.blogs (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.category_details
    OWNER to postgres;
```

### Subscribers table

```sql
CREATE TABLE IF NOT EXISTS public.subscribers
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    email text COLLATE pg_catalog."default" NOT NULL,
    full_name text COLLATE pg_catalog."default" NOT NULL,
    create_at timestamp without time zone NOT NULL,
    update_at timestamp without time zone NOT NULL,
    delete_at timestamp without time zone,
    CONSTRAINT subscribers_pkey PRIMARY KEY (id),
    CONSTRAINT "Unique subscriber email" UNIQUE (email)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.subscribers
    OWNER to postgres;
```

## Run project

**Requirements:**

- IDE: Intellij IDEA Ultimate version
- DBMS: PostgreSQL
- DBMS client: can use Intellij IDEA intergrated database connection or PgAdmin app.
- Tomcat: version 9

**Step-by-step guide:**

1. Clone the project.
2. Intergrate Tomcat into IDE.
3. Open the project inside Intellij.
4. Configure the `spring-config-mvc.xml` file.

  Since there are a differences in database configuration of each computer, I will ignore the bean configuration file and let you config it yourself with the file template:
  
  `spring-config-mvc.xml`

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:p="http://www.springframework.org/schema/p"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/tx
          http://www.springframework.org/schema/tx/spring-tx.xsd
          http://www.springframework.org/schema/mvc
          http://www.springframework.org/schema/mvc/spring-mvc.xsd">

        <!-- Spring MVC Annotation -->
        <context:annotation-config />
        <mvc:annotation-driven/>

      <!-- * This bean is for file load -->
      <!--    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">-->
      <!--        <property name="maxUploadSize" value="20971520"/>-->
      <!--    </bean>-->

      <!-- * This bean is for mail sending service -->
      <!--    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">-->
      <!--        <property name="host" value="smtp.gmail.com"/>-->
      <!--        <property name="port" value="587"/>-->
      <!--        <property name="username" value="nnminh.sam.1803@gmail.com"/>-->
      <!--        <property name="password" value="gleetqoxykdjalws"/>-->
      <!--        <property name="defaultEncoding" value="UTF-8"/>-->
      <!--        <property name="javaMailProperties">-->
      <!--            <props>-->
      <!--                <prop key="mail.smtp.auth">true</prop>-->
      <!--                <prop key="mail.smtp.socketFactory.class">javax.net.ssl.SSLSocketFactory</prop>-->
      <!--                <prop key="mail.smtp.socketFactory.port">465</prop>-->
      <!--                <prop key="mail.smtp.starttls.enable">false</prop>-->
      <!--                <prop key="mail.debug">true</prop>-->
      <!--            </props>-->
      <!--        </property>-->
      <!--    </bean>-->

        <!-- TODO: configure your data source here -->
        <bean id="dataSource"
              class="org.springframework.jdbc.datasource.DriverManagerDataSource"
              p:driverClassName="org.postgresql.Driver" 
              p:url="jdbc:postgresql://localhost:<Database port>/blog-platform"
              p:username="<login name>"
              p:password="<password>">
        </bean>

        <bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
            <property name="dataSource" ref="dataSource"/>
            <property name="hibernateProperties">
                <props>
                    <prop key="hibernate.dialect">org.hibernate.dialect.PostgresPlusDialect</prop>
                    <prop key="hibernate.show_sql">true</prop>
                </props>
            </property>
            <property name="packagesToScan" value="com.proj.projblogplatform.model"/>
        </bean>

        <bean id="transactionManager"
              class="org.springframework.orm.hibernate4.HibernateTransactionManager"
              p:sessionFactory-ref="sessionFactory"/>
        <tx:annotation-driven transaction-manager="transactionManager"/>

        <bean id="viewResolver"
              p:prefix="/WEB-INF/views/" p:suffix=".jsp"
              class="org.springframework.web.servlet.view.InternalResourceViewResolver"/>

        <context:component-scan base-package="com.proj.projblogplatform.controller"/>
        <context:component-scan base-package="com.proj.projblogplatform.service"/>
        <context:component-scan base-package="com.proj.projblogplatform.repository"/>
        <context:component-scan base-package="com.proj.projblogplatform.model"/>
    </beans>
  ```

  Inside the file, there will be a TODO section, read it carefully and fillout the missing data `<data>` and copy this file into `src/mainwebapp/WEB-INF/configs/spring-config-mvc.xml` file in your project directory.

  Note that when you create the `.xml` file inside Intellij, the IDE will put a small promt that the top of the editor, it's asking you if you want to add this file as a spring configuration for the project, you just need to follow the instructions and you should be ok.

5. Run the app.
