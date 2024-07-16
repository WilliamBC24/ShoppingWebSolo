# ShoppingWebSolo
My SWP project for a shopping website, originally planned as a group effort, has now become a solo initiative due to the incompetence of team members.

Ensure that you have Apache Tomcat 10 installed on your system. If you intend to use the projects in Visual Studio Code, make sure to include the required JAR libraries and Apache Tomcat server libraries in your settings.json as follows:

```json
{
    "java.project.referencedLibraries": [
        "../Libraries/*.jar",
        "/usr/share/tomcat10/lib/*.jar"
    ]
}
```

Or as follow if you are on Windows:

```json
{
    "java.project.referencedLibraries": [
        "..\\Libraries\\*.jar",
        "C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1\\lib\\*.jar"
    ]
}
```
