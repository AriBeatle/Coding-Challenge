from rest_framework import serializers
from .models import Trainer

class TrainerSerializer(serializers.ModelSerializer): 

    # Define the fields to be serialized
    class Meta:
        model = Trainer
        fields = ('id', 'email', 'phone', 'first_name', 'last_name', 'zip_code', 'certified', 'background')

    # Format the trainer id as it's expected
    def to_representation(self, instance):
         data = super().to_representation(instance)
         data['id'] = "trainer-id-{:06d}".format(data['id'])
         return data