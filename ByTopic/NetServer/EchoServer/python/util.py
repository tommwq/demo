
def noexcept(function):
    '''忽略函数中的异常'''
    def noexcept_wrapper(*args, **kw):
        try:
            return function(*args, **kw)
        except:
            return None
        
    return noexcept_wrapper


def local_time_string():
    '''显示本地时间字符串'''
    import datetime
    return datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f") 


def trace(function):
    '''在进入函数前和离开函数后打印'''
    def trace_wrapper(*args, **kw):
        function_name = function.__qualname__
        print(local_time_string(), 'enter', function_name)
        try:
            result = function(*args, **kw)
            print(local_time_string(), 'leave', function_name)
        except e as Error:
            print(local_time_string(), 'leave', function_name, 'on error', e)
            raise e

    return trace_wrapper
    
