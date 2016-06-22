# view-sample

View Sample - This is a repository that holds a standards based JAX-RS API - partial reference implementation by Philip Paz. Jersey RESTful Web Services framework is open source, production quality, and framework for developing RESTful Web Services in Java that provides support for JAX-RS APIs and serves as a JAX-RS (JSR 311 & JSR 339) Reference Implementation.

Jersey compliments by providing its own API that extends the JAX-RS toolkit with additional features and utilities to further simplify RESTful service and client development.

The repository also includes, for reference, the applicatoinContext.xml used for @Autowired of bean jdbcTemplate in the data access object. I have included the pom.xml to show project compile dependencies / external libraries used. Spring is utilized for core, context, beans, annotations, etc...

<b>View Sample includes the following packages and java source</b>

<b>com.rest.json.app</b>        
&nbsp;&nbsp;-- Applicatoin.java<br>
<b>com.rest.json.bean</b>       
&nbsp;&nbsp;-- ManufacturerBean.java<br>
<b>com.rest.json.dao</b>       	
&nbsp;&nbsp;-- ManufacturerDAO.java<br>
<b>com.rest.json.filter</b>    	
&nbsp;&nbsp;-- PoweredByResponseFilter.java<br>
<b>com.rest.json.resource</b>  	
&nbsp;&nbsp;-- ManufacturerResource.java<br>
<b>config</b>					
&nbsp;&nbsp;-- ApplicatoinContext.xml<br>
<b>maven</b>					
&nbsp;&nbsp;-- pom.xml<br>

Thanks for dropping by, appreciate your time...
