🚀 Nexperia AEM Challenge Project

This repository contains the solution for the Nexperia AEM Coding Challenge.
It is built using Java, Maven, and follows the Adobe Experience Manager (AEM) project structure with core and UI modules.

📂 Project Structure
Nexperia_AEM_Challenge_Final/
├── core/         # Core Java code (services, models, servlets)
├── ui.apps/      # AEM components and client libraries
├── ui.content/   # Content package for testing in AEM
├── pom.xml       # Parent Maven configuration
└── README.md     # Project documentation

⚙️ Prerequisites

Java 11 or later

Apache Maven 3.6+

Adobe AEM SDK installed and running (AEM 6.5 or AEMaaCS)

🔨 Build & Deploy
Build Project
mvn clean install

Deploy to AEM
mvn clean install -PautoInstallPackage


This will install the generated packages into your running AEM instance
(default: http://localhost:4502
).

✨ Features

✅ Core services and models written in Java

✅ AEM components structured in ui.apps

✅ Sample content included in ui.content

✅ Maven multi-module project structure

✅ Compatible with AEM 6.5 / AEMaaCS
