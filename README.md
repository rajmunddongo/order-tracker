# Order tracker web app

Order tracker web application.

Featured technologies:

- Spring boot [Backend]
- Spring Security
- JWT authentication
- Angular [Frontend]
- JPA

To try out this app you will need postgres running and you need an empty database.
In the application.properties you can modify this line: 
spring.datasource.url=jdbc:postgresql://localhost:5432/ordertracker

Also this application features Stripe integration for payments, however this feature will require an API key to work.
You can provide the API key by adding a record into the payment service Credentials table.

Not only stripe but the email integration too, will require an API key which you can provide in the ordertracker 
service application.properties file.

Moreover as I were trying to make this webapp customisable, the webapp will load various pictures from database so 
before the first start you will have to provide them.(I added a max limit of 1mb for them) These pictures are the 
following:

After providing the necessary images and API keys you can register customers and merchants using the UI and as a 
merchant you will be able to register products and manage your orders.

As a customer you will have the opportunity to browse the merchants and order products you like after paying for 
them with stripe.

To reach the administration page, you will have to manually insert a record into the users table with ADMIN role.

