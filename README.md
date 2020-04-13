# CS1660_Final
Demo Link   https://youtu.be/40np64t254w

I completed the 3 main goals

  The java application is able to execute on docker, 
  I can communicate to GCP partially, 
  I have Inverted Indexing running on the cluster. 
  

To build the docker image 
  docker build -t myapp .
To run the app I used 
  docker run -it --privileged -e DISPLAY=$DISPLAY -v /tmp/.X11-unix:/tmp/.X11-unix -e
  GOOGLE_APPLICATION_CREDENTIALS=/usr/src/myapp/creds.json myapp

Maven was also used to allow me to connect to GCP.



