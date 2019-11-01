FROM php:7-alpine
COPY . /var/www/html
WORKDIR /var/www/html
RUN apk update && apk add curl && \
  curl -sS https://getcomposer.org/installer | php \
  && chmod +x composer.phar && mv composer.phar /usr/local/bin/composer
RUN composer install
RUN docker-php-ext-install pdo pdo_mysql mysqli
CMD [ "php", "-S", "0.0.0.0:8080",  "-t", "public" ]