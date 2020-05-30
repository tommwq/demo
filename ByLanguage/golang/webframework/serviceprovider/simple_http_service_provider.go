package serviceprovider

import (
	"fmt"
	"log"
	"reflect"
	"strings"

	"tq/webframework/service"
)

type SimpleHTTPServiceProvider struct {
	routeTable map[string]interface{}
}

func NewSimpleHTTPServiceProvider() *SimpleHTTPServiceProvider {
	return &SimpleHTTPServiceProvider{}
}

type Echo struct{}

// TODO return ([]byte, renderRequest, error)
func (r *Echo) Echo(text string) ([]byte, error) {
	return []byte("ECHO: " + text), nil
}

// TODO 把Serve的参数httpContext改为Model
func (r *SimpleHTTPServiceProvider) Serve(httpContext interface{}) interface{} {
	log.Printf("SimpleHTTPServiceProvider.Serve(%v)\n", httpContext)
	if context, ok := httpContext.(*HTTPContext); ok {
		return r.process(context)
	}

	return nil
}

func NewSimpleHTTPService() *service.Service {
	return service.NewService(NewSimpleHTTPServiceProvider())
}

func (r *SimpleHTTPServiceProvider) Consume(httpContext interface{}) {
	if context, ok := httpContext.(*HTTPContext); ok {
		go fmt.Fprint(context.Writer, context.Content)
	}
}

func (r *SimpleHTTPServiceProvider) process(context *HTTPContext) interface{} {
	path := context.Request.URL.Path

	// TODO
	// Find controller and method
	// Map param to object
	// invoke

	pieces := strings.Split(path, "/")
	name := pieces[1]

	m := make(map[string]interface{})
	m["Echo"] = (*Echo)(nil)

	log.Printf("name = %v, path = %v\n", name, path)
	if name == "Echo" {

		e := m[name]
		t := reflect.TypeOf(e)
		method := pieces[2]
		fn, ok := t.MethodByName(method)
		if !ok {
			context.Content = []byte("method not found")
		} else {
			for i := 0; i < fn.Func.Type().NumIn(); i++ {
				x := fn.Func.Type().In(i)
				log.Println(x.Name(), x.String(), x.Kind())
			}

			values := fn.Func.Call([]reflect.Value{reflect.Zero(t),
				reflect.ValueOf("OK123")})
			bytes := values[0].Bytes()
			context.Content = bytes
		}

	} else if path == "/favicon.ico" {
		context.Content = []byte("NOT FOUND")
	} else {
		text := "PATH: " + path
		context.Content = []byte(text + "123")
	}

	return context
}
