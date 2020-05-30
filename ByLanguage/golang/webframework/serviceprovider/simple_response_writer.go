package serviceprovider

import (
	"log"
)

type SimpleResponseWriter struct{}

func NewSimpleResponseWriter() *SimpleResponseWriter {
	return &SimpleResponseWriter{}
}

func (r *SimpleResponseWriter) Consume(httpContext interface{}) {
	log.Printf("SimpleResponseWriter.Consume(%v)\n", httpContext)
	if context, ok := httpContext.(*HTTPContext); ok {
		log.Printf("SimpleResponseWriter output [%v]\n", string(context.Content))
		size, err := context.Writer.Write(context.Content)
		if err != nil {
			log.Printf("SimpleResponseWriter fail to write. size: %v. error: %v.\n", size, err)
		}
		context.Done <- true

		return
	}

	log.Printf("SimpleResponseWriter does not recognize payload %v\n", httpContext)
}
