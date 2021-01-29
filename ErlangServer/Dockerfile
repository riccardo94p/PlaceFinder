FROM ubuntu
ENV TZ=Europe/Rome
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN apt-get update
RUN apt-get install build-essential -y
RUN apt-get install erlang -y
RUN apt-get install openjdk-11-jdk --yes
RUN apt-get install maven -y
RUN apt-get install net-tools -y
RUN apt-get install iputils-ping -y
EXPOSE 4369