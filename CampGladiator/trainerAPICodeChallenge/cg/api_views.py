from rest_framework.exceptions import ValidationError
from rest_framework.generics import CreateAPIView, ListAPIView, RetrieveUpdateDestroyAPIView

from .models import Trainer
from .serializers import TrainerSerializer
import re

# Create new trainer (POST)
class TrainerCreate(CreateAPIView):
    serializer_class = TrainerSerializer

# Retrieve (GET), Update (UPDATE/PATCH), Destroy (DELETE)
class TrainerRetrieveUpdateDestroy(RetrieveUpdateDestroyAPIView):
    queryset = Trainer.objects.all()
    serializer_class = TrainerSerializer

    def get_object(self):
        pattern = r'trainer-id-[0]*'
        id = re.split(pattern, self.kwargs.get('id'))[1] 
        return Trainer.objects.get(id=id)
    
    def delete(self, request, *args, **kwargs):
        trainer_id = request.data.get('id')
        response = super().delete(request, *args, **kwargs)
        if response.status_code == 204:
            # Delete the object from the cache
            from django.core.cache import cache
            cache.delete('trainer_data_{}'.format(trainer_id))
        return response

    def update(self, request, *args, **kwargs):
        response = super().update(request, *args, **kwargs)
        if response.status_code == 200:
            # Update the object in the cache
            from django.core.cache import cache
            trainer = response.data
            cache.set('trainer_data_{}'.format(trainer['id']), {
                'first_name': trainer['first_name'],
                'last_name': trainer['last_name'],
                'email': trainer['email'],
                'phone': trainer['phone'],
                'zip_code': trainer['zip_code'],
                'certified': trainer['certified'],
                'background': trainer['background'],
            })
        return response

# Get all the trainers
class TrainerList(ListAPIView):
    queryset = Trainer.objects.all()
    serializer_class = TrainerSerializer