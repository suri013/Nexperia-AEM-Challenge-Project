# Nexperia AEM Challenge Project

This project is a part of the **Nexperia AEM Challenge**, developed using **Java, Maven, AEM SDK, OSGi framework, and Sling Models**.  

## 📂 Project Structure
nexperia-aem-challenge/
├── core/ # Core backend code
│ ├── blog/ # Blog service and implementation
│ ├── models/ # Sling models
│ └── servlets/ # AEM Servlets
├── ui.apps/ # Frontend components
├── ui.content/ # Content structure
├── all/ # Dispatcher, config
└── pom.xml # Maven parent build file


## 🚀 Features
- Blog Service with implementation  
- Sling Models for AEM components  
- OSGi configurations  
- Servlets for custom backend logic  
- Maven multi-module project structure  

## 🛠️ Requirements
- Java 11+  
- Maven 3.8+  
- AEM SDK running instance  
- Git  

## ▶️ Setup & Run
```bash
# Clone the repository
git clone https://github.com/suri013/Nexperia-AEM-Challenge-Project.git
cd Nexperia-AEM-Challenge-Project

# Build with Maven
mvn clean install

# Deploy to AEM
mvn -PautoInstallPackage clean install
