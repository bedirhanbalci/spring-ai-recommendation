# spring-ai-recommendation


## API Documentation


# Save user
POST http://localhost:8080/api/v1/users
content-type: application/json

{
	"name": "Adem",
    "email": "ademozalp57@gmail.com",
    "password": "123456"
}

<> 2024-07-30T133341.200.json

###
# Cahnge user status
PUT localhost:8080/api/v1/users/ademozalp57@gmail.com?statusType=APPROVED

###
# Create blog
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
   "title": "API Gateway (Ağ Geçidi) Nedir?",
   "text": "Bir çok projede kullandığımız api, REST api vb. çağırımlarının her birinin yaptığı işlevi göz önüne alırsak ne kadar önemli olduğu kuşkusuz bir gerçek. Ancak bir o kadar önemli konu daha var. Güvenlik ve orkestrasyon. Kullandığımız bu apileri tek bir yerden yönetmek, yönlendirmek eve giriş kapısının bir yerden olduğu gibi önemlidir. Evin içerisine kimin girdiği ne yaptığını ilk aşamada kontrol ederiz. Bu sebeple api ağ geçidi bu konuda bizlere yardımcı oluyor. Ne olduğunu açıklamaya başlayalım."
}

<> f-27.txt


###
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
     "title": "Load Balancer nedir ve ne işe yarar?",
     "text": "Bu flood’da iş yükünü dağıtarak uygulamaların ölçeklenmesini sağlayan Load Balancer (Yük Dengeleyici)’lardan bahsedeceğim.Modern mimarilerinde artan trafiği karşılamak için mevcut kaynakları (CPU, memory) artırmak yerine yeni sunucu eklenmesi tercih edilmektedir. Mevcut kaynakları artırmak scale-up, yeni sunucular eklemekse scale-out olarak bilinir."
}

<> f-28.txt


###
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
               "title": "How to implement Zipkin in Spring Boot 3",
            "text": "To implement Zipkin in a Spring Boot application and run it using Docker Compose, follow these steps:"
}

<> f-29.txt

###
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
     "title": "Spring AI Integration with OpenAI",
     "text": "Spring AI applies the portability and modular design principles of the Spring ecosystem to the AI domain while developing AI applications. It helps in developing AI applications using Java objects."
}

<> f-30.txt


###
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
               "title": "Method Idempotence Nedir?",
            "text": "Bir metodun bir defa çağrıldığında alınan sonuç ile birden fazla kez çağrıldığında alınan sonuç aynı ise bu bir idempotent metottur."
}

<> f-31.txt


###
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
          "title": "SonarQube Nedir ve Spring Boot Projemize Nasıl Entegre Ederiz?",
          "text": "Merhaba, bu yazıda projelerimizde kullanmamızda büyük fayda olan bir araçtan bahsedeceğim. Daha sonra, bir çok farklı teknoloji ile entegre olabilen bu aracı Spring Boot projemize entegre edeceğiz ve bazı detayları inceleyeceğiz. Aşağıdaki maddeler üzerinden devam edelim:"
}

<> f.txt


###
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
               "title": "File uploading with Spring Boot & Firebase Cloud Storage.",
            "text": "Today I am going to show how you can upload images using Spring Boot and store them in the Firebase Cloud Storage."
}

<> f-1.txt


###
POST localhost:8080/api/v1/blogs/users/ademozalp57@gmail.com
content-type: application/json

{
               "title": "Microservice Mimarilerinde Service Discovery",
            "text": "Merhaba.Bu yazıda microservice mimarilerinin temel konularından biri olduğunu düşündüğüm Service Discovery konusundan bahsetmek istiyorum."
}

<> f-32.txt


###

#Get all blogs
GET http://localhost:8080/api/v1/blogs

<> 2024-07-30T133342.200.json


###
# Get recommended blogs
GET http://localhost:8080/api/v1/blogs/recommendations
content-type: application/json

[
  "Microservice Mimarilerinde Service Discovery",
  "SonarQube Nedir ve Spring Boot Projemize Nasıl Entegre Ederiz?"
]

<> 2024-07-30T133515.200.json

###




GET http://localhost:8080/api/v1/blogs/read/{title}

###

