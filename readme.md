
--Instructions for running the project.
1. maven build command-
			mvn clean install jib:dockerBuild -Djib.to.image=url-hashing-image
			
2. running the above created docker image-			
			docker run -p 50000:50000 url-hashing-image
			
Note: This needs docker daemon running.		

--Calling the API end-points.
1. For generating the short URL and persisting the details in h2 database...
POST - http://localhost:50000/short?url=https://zahir.com/projects/P9/repos/integration-spec/browse/src/main/resources/schemas/definitions.json
2. For retrieving the Original URL using the generated short URL...
GET - http://localhost:50000/long?tiny=3ae74dd9

--Spring scheduled task for purging the URLs
1. Spring scheduled task runs every 5mins with the initial delay delay of 5mins.
2. It will purge the URLs which are older than 30mins