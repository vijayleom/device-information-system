# Device Information System

A REST API capable solution with the OpenAPI documentation on its endpoints that is feature ready to run on JDK21. OpenAPI definition of the endpoints can be found [here](http://localhost:8080/swagger-ui/index.html)

## Installation

Have the JAVA JDK21 in your runtime path with Apache Maven. Once those are available, Just clone the project into you local and execute it as in below.

```bash
mvn spring-boot:run
```
This project is enabled for execution as Docker Image. To build the image, Run the below command, Ensure you have docker or rancher running active on your machine.

```bash
docker build -t device-info-sys:latest .
```

So once the above command is executor, the image gets built and made available in your local container location depending on the OS, however readily can be seen on your RancherDesktop or Docker app. Now to run this image from the CLI, use the below command:

```bash
docker run -p 8080:8080 --name device-store device-info-sys:latest
```

To verify if there is active process in execution, try the below command to affirm the executing process. Container Name will be "device-store", FYI!

```bash
docker ps
```

## Features

This project stores the data using H2's File Based(Not in Memory). So that this makes a persistent solution and can help recover after the app is resumed as well. Also, the Application is covered with the below endpoints:

- Create a new device.
- Fully and/or par:ally update an exis:ng device.
- Fetch a single device.
- Fetch all devices.
- Fetch devices by brand.
- Fetch devices by state.
- Delete a single device.

We have leveraged usage of LOMBOK effectively. At present, we have the minimal offerring - Future scope of development includes:

- Using JDK21's Record class as DTO with primary validations done in an annotated way. This would avoid POJOs.
- RECORD PATTERN helps us to use more specific actions specific to the cases like IU(In-Use), AV(Available) to be more modular and readable.
- JDK21 comes with the Project Bloom's offerring of Virtual Threads which are JVM managed lightweighted threads, unlike the traditional platform threads. Need to explore more on incorporating to the project.
- Project is covered with the API & Data JPA test. As part of BDD, this project can be leveraged to use KARATE API Testing, Which is industry best for API testing.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

TBA