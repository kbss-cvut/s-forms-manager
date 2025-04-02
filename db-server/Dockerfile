# Use an official RDF4J server image
FROM eclipse/rdf4j-workbench:5.1.2

# Set the working directory in the container
WORKDIR /var

# Copy the startup script to the container
COPY /data/startup.sh /rdf4j/startup.sh

USER root

RUN mkdir /temp

COPY /data/s-forms-manager /temp/s-forms-manager

# Make the startup script executable
RUN chmod +x /rdf4j/startup.sh

# Expose the RDF4J server port
EXPOSE 8080

# Run the RDF4J server
CMD ["/rdf4j/startup.sh"]