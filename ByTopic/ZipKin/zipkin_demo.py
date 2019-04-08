import random
import requests
import time

from py_zipkin.zipkin import zipkin_span
from py_zipkin.transport import BaseTransportHandler

class HttpTransport(BaseTransportHandler):

    def get_max_payload_bytes(self):
        return None

    def send(self, encoded_span):
        # The collector expects a thrift-encoded list of spans.
        requests.post(
            'http://localhost:9411/api/v1/spans',
            data=encoded_span,
            headers={'Content-Type': 'application/x-thrift'},
        )


def do_stuff(a, b):
    time.sleep(random.random())

def some_function(a, b):
    with zipkin_span(
            service_name='my_service',
            span_name='my_span_name',
            transport_handler=HttpTransport(),
            port=9411,
            sample_rate=100, #0.05, # Value between 0.0 and 100.0
    ):
        do_stuff(a, b)

for x in range(0, 100):        
    some_function(1, 2)
