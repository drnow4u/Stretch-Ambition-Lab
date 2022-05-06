module user.service {
    requires spring.core;
    requires java.validation;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.context;
    requires java.transaction;
    exports com.gitlab.marnow.stretchambitionlab.springpostgresbase.service.user;
}