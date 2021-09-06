# Generated by Django 3.2.5 on 2021-09-06 17:57

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Trainer',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('firstName', models.CharField(max_length=50)),
                ('lastName', models.CharField(max_length=50)),
                ('email', models.EmailField(max_length=254)),
                ('phone', models.CharField(max_length=20)),
                ('zipCode', models.CharField(max_length=5)),
                ('certTrainer', models.BooleanField(default=False)),
                ('background', models.TextField(blank=True, max_length=150)),
            ],
        ),
    ]