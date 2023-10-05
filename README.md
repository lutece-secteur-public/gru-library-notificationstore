## library-notificationstore
Client library to request  GRU user notifications and demands

# Usage 

# Configuration

Example of configuration with direct access : 

    <!-- IHttpTransportProvider declarations -->
    <bean id="notificationStore.httpAccessTransport" class="fr.paris.lutece.plugins.notificationstore.v1.web.rs.service.HttpAccessTransport" >
		    <property name="ApiEndPointUrl">
            <value>${myplugin.notificationStore.ApiEndPointUrl}</value>
        </property>
     </bean>

    <bean id="notificationStore.restTransport.httpAccess" class="fr.paris.lutece.plugins.notificationstore.v3.web.rs.service.NotificationStoreTransportRest">
        <constructor-arg ref="notificationStore.httpAccessTransport"/>
    </bean>
    
    <!-- Notification Store Service impl -->
    <bean id="notificationStore.notificationStoreService" class="fr.paris.lutece.plugins.notificationstore.v1.web.service.NotificationStoreService">
        <constructor-arg ref="notificationStore.restTransport.httpAccess"/>
    </bean>

Example with API Manager config :

    <!-- IHttpTransportProvider declarations -->
    <bean id="notificationStore.httpAccessTransport" class="fr.paris.lutece.plugins.notificationstore.v1.web.rs.service.HttpApiManagerAccessTransport" >
	<property name="ApiEndPointUrl">
            <value>${myplugin.notificationStore.ApiEndPointUrl}</value>
        </property>
	<property name="AccessManagerEndPointUrl">
            <value>${myplugin.notificationStore.AccessManagerEndPointUrl}</value>
        </property>
	<property name="AccessManagerCredentials">
            <value>${myplugin.notificationStore.AccessManagerCredentials}</value>
        </property>
     </bean>

    <bean id="notificationStore.restTransport.httpAccess" class="fr.paris.lutece.plugins.notificationstore.v1.web.rs.service.NotificationStoreTransportRest">
        <constructor-arg ref="notificationStore.httpAccessTransport"/>
    </bean>

    <!-- Notification Store Service impl -->
    <bean id="notificationStore.notificationStoreService" class="fr.paris.lutece.plugins.notificationstore.v1.web.service.NotificationStoreService">
        <constructor-arg ref="notificationStore.restTransport.httpAccess"/>
    </bean>

Example for Mock configuration :

    <!-- Mock store -->
    <bean id="notificationStore.mock" class="fr.paris.lutece.plugins.notificationstore.v1.web.rs.service.NotificationStoreMock">
    </bean>

    <!-- Notification Store Service impl -->
    <bean id="notificationStore.notificationStoreService" class="fr.paris.lutece.plugins.notificationstore.v1.web.service.NotificationStoreService">
        <constructor-arg ref="notificationStore.mock"/>
    </bean>