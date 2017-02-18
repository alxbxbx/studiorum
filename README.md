# Studiorum
This is e-learning application developed by students for Facutly of Technical Science, Novi Sad.

# Developer Environment Setup
- Install Java
- Install Maven
- Install Ruby
- Install Ruby's gem compass
  - *gem install compass*
- Install Node.js
- Install Node.js's modul grunt-cli and bower globaly
  - *npm install grunt-cli  -g* (globally)
  - *npm install bower -g* (globally)
- In project root folder, install bower dependencies and Node.js local modules
  - *bower install*
  - *npm install* 
- Run Grunt to generate static files for development
  - *grunt --force buildDev*
- Create application.properties in src/main/resources relative to application.properties.dist
  - *set storage path (destination for uploaded documents)*
  - *set picture_storage path (destination for uploaded pictures)*