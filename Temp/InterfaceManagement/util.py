

def get_mime(file_name):
    mime_table = {
        '.js': 'application/javascript',
        '.css': 'text/css',
        '.html': 'text/html',
    }
    
    for suffix, mime in mime_table.items():
        if file_name.endswith(suffix):
            return mime

    return 'text/html'


def to_dict_list(object_list):
    '''将对象列表转换为字典列表。'''
    return [x.__dict__ for x in object_list]
