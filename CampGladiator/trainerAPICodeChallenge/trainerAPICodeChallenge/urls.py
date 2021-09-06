"""trainerAPICodeChallenge URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
import cg.api_views

urlpatterns = [
    path('api/trainers/new', cg.api_views.TrainerCreate.as_view()), #Create a Trainer
    path('api/trainers/<str:id>/', cg.api_views.TrainerRetrieveUpdateDestroy.as_view()), #Get, Update, Delete Trainer
    path('api/trainers/', cg.api_views.TrainerList.as_view()), #List all Trainers   

    path('admin/', admin.site.urls),
]
