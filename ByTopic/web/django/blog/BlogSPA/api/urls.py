from django.urls import path
from . import views

urlpatterns = [
    path('login', views.login),
    path('register', views.register),
    path('blog', views.blog),
    path('blog/<int:blog_id>', views.blog_detail),
]
