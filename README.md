# Nexperia AEM Challenge Project

This project is a solution for the **Nexperia AEM Coding Challenge**.  
It is built using **Java**, **Maven**, and follows the structure for an **AEM project** with core and UI components.  

---

## 📂 Project Structure
Nexperia_AEM_Challenge_Final/
├── core/ # Core Java code (services, models, servlets)
├── ui.apps/ # AEM components and clientlibs
├── ui.content/ # Content package for testing in AEM
├── pom.xml # Parent Maven configuration
└── README.md # Project documentation

yaml
Copy
Edit

---

## 🚀 How to Build & Run

### Prerequisites
- Java 11 or later  
- Apache Maven 3.6+  
- Adobe AEM SDK installed and running  

### Build Project
```bash
mvn clean install
Deploy to AEM
bash
Copy
Edit
mvn clean install -PautoInstallPackage
This will install the packages into your running AEM instance (usually at http://localhost:4502).
