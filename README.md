# YoutubeCrawler
Scalable framework for data collection from YouTube

<h2>How to launch the app</h2>
1. Download Apache ActiveMq from http://activemq.apache.org
2. Inside ActiveMq bin directory call 
    > activemq start
3. Do Maven clean & install in core module
4. To start rest api application, call springboot:run inside rest-api module
5. To start worker application, call springboot:run inside worker module
6. All rest-api endpoints will be available at localhost:<your port>/swagger-ui.html
