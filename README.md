## 🚀 Distributed URL Shortener (Spring Boot + Redis + MySQL + Rate Limiter)
A production-style distributed URL Shortener system built using Spring Boot, Redis, MySQL, and a dedicated Rate Limiter Service using Redis + Lua scripting.
This project demonstrates:


- Distributed system architecture
- Redis cache-aside pattern
- Base62 URL generation
- Collision handling
- Service-to-service communication
- 301/302 redirects
- Expiry-based URLs
- Analytics tracking
- Distributed rate limiting



## ✨ Key Features

- 🔗 URL Shortening
- Base62 encoding
- Obfuscated short code generation
- Collision handling

## ⚡ Redis Cache Integration

-Cache-aside pattern
-Fast redirect lookup
- Reduced DB load

## 🔄 Distributed Rate Limiting

- Dedicated Rate Limiter microservice
- Redis + Lua atomic operations
- Token Bucket support

## 📊 Analytics Tracking

- Click count tracking
- Redirect statistics
  
## ⏳ Expiry-based URLs

- Optional URL expiration
- Time-based validation

## 🌐 301 / 302 Redirect Support

-Permanent redirects
- Temporary redirects

## 🧩 Service-to-Service Communication

- URL Shortener ↔ Rate Limiter
- REST-based internal communication

  ## url to the rate limiter
  https://github.com/vishalshaw007/rate-limiter-url-shortner-

 ## 👨‍💻 Author
## Vishal Shaw

## ⭐ If you found this useful, give it a star!




