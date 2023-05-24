CREATE TABLE "users"(
    "user_id" BIGINT NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "login" VARCHAR(255) NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "birthday" DATE NOT NULL
);
ALTER TABLE
    "users" ADD PRIMARY KEY("user_id");
CREATE TABLE "likes"(
    "film_id" BIGINT NOT NULL,
    "user_id" BIGINT NOT NULL
);
ALTER TABLE
    "likes" ADD PRIMARY KEY("film_id");
CREATE TABLE "films"(
    "film_id" BIGINT NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "release_date" DATE NOT NULL,
    "duration" INTEGER NOT NULL,
    "genre_id" INTEGER NOT NULL,
    "rating_id" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "films" ADD PRIMARY KEY("film_id");
CREATE TABLE "rating"(
    "rating_id" INTEGER NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "rating" ADD PRIMARY KEY("rating_id");
CREATE TABLE "friends"(
    "user_id" BIGINT NOT NULL,
    "friend_id" BIGINT NOT NULL,
    "friendship" BOOLEAN NOT NULL
);
ALTER TABLE
    "friends" ADD PRIMARY KEY("user_id");
CREATE TABLE "genre"(
    "genre_id" INTEGER NOT NULL,
    "name" VARCHAR(255) NOT NULL
);
ALTER TABLE
    "genre" ADD PRIMARY KEY("genre_id");
ALTER TABLE
    "friends" ADD CONSTRAINT "friends_friend_id_foreign" FOREIGN KEY("friend_id") REFERENCES "users"("user_id");
ALTER TABLE
    "films" ADD CONSTRAINT "films_genre_id_foreign" FOREIGN KEY("genre_id") REFERENCES "genre"("genre_id");
ALTER TABLE
    "friends" ADD CONSTRAINT "friends_user_id_foreign" FOREIGN KEY("user_id") REFERENCES "users"("user_id");
ALTER TABLE
    "films" ADD CONSTRAINT "films_rating_id_foreign" FOREIGN KEY("rating_id") REFERENCES "rating"("rating_id");
ALTER TABLE
    "likes" ADD CONSTRAINT "likes_film_id_foreign" FOREIGN KEY("film_id") REFERENCES "films"("film_id");
ALTER TABLE
    "likes" ADD CONSTRAINT "likes_user_id_foreign" FOREIGN KEY("user_id") REFERENCES "users"("user_id");