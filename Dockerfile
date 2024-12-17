FROM jenkins/jenkins:lts
USER root
RUN apt-get update && apt-get install -y \
    git \
    maven \
    && rm -rf /var/lib/apt/lists/*
USER jenkins
