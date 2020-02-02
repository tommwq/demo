import inspect
import functools

def check_signature(function):
    annos = function.__annotations__
    assert "return" in annos, "missing type for return value"

    spec = inspect.getfullargspec(function)
    for arg in spec.args + spec.kwonlyargs:
        assert arg in annos, ("missing type for parameter '" + arg + "'")

    @functools.wraps(function)
    def wrapper(*args, **kwargs):
        for name, arg in (list(zip(spec.args, args)) + list(kwargs.items())):
            assert isinstance(arg, annos[name]), ("expected argument '{0}' of {1} go {2}".format(name, annos[name], type(arg)))

        result = function(*args, **kwargs)
        assert isinstance(result, annos["return"]), ("expected return of {0} got {1}".format(annos["return"], type(result)))
        return result
    return wrapper


@check_signature
def foo(i: int) -> str:
        return str(i)


print(foo(1))
print(foo('1')) # error
