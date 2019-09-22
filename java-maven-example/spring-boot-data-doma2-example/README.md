Supports the X-ray trace

 - Use the Tomcat JDBC Pool
 - Configure to application.yml to 

   ex)
   ```yaml
   spring:
     datasource:
       tomcat.jdbc-interceptors: com.amazonaws.xray.sql.mysql.TracingInterceptor
   ```

## See
https://docs.aws.amazon.com/ja_jp/xray/latest/devguide/xray-sdk-java-configuration.html

