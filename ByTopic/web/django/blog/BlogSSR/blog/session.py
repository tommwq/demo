from django.http import request

_USER_ID_KEY = '_USER_ID_KEY'
_USERNAME_KEY = '_USERNAME_KEY'

class Session(object):
    def __init__(self, request):
        self._session = request.session

    def is_logged_in(self):
        return _USER_ID_KEY in self._session

    def login_as(self, user_id, username):
        self._session[_USER_ID_KEY] = user_id
        self._session[_USERNAME_KEY] = username

    def logout(self):
        del self._session[_USER_ID_KEY]
        del self._session[_USERNAME_KEY]
        
    def user_id(self):
        if self.is_logged_in():
            return self._session[_USER_ID_KEY]
        else:
            return None
