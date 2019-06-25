This is the monitor microservice.

It consumes order messages from the queue and maintains a sliding window of the last ten orders per state in a local cache.

An alternative implementation may use an external cache to allow this service to be scalable.