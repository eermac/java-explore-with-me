DROP TABLE IF EXISTS categories, users, location, events, compilations, requests, compilations_events CASCADE;

CREATE TABLE IF NOT EXISTS categories (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) UNIQUE,
  CONSTRAINT pk_categories PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) UNIQUE,
  email VARCHAR(255) NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS location (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  lat REAL NOT NULL,
  lon REAL NOT NULL,
  CONSTRAINT pk_location PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  annotation VARCHAR(2048) NOT NULL,
  category_id BIGINT NOT NULL,
  description VARCHAR(7000) NOT NULL,
  confirmed_requests BIGINT NOT NULL,
  initiator_id BIGINT NOT NULL,
  event_date TIMESTAMP WITHOUT TIME ZONE,
  location_id BIGINT NOT NULL,
  paid BOOLEAN,
  participant_limit BIGINT NOT NULL,
  request_moderation BOOLEAN,
  created TIMESTAMP WITHOUT TIME ZONE,
  state VARCHAR(255) NOT NULL,
  title VARCHAR(255) NOT NULL,
  published TIMESTAMP WITHOUT TIME ZONE,
  views BIGINT NOT NULL,
  CONSTRAINT pk_events PRIMARY KEY(id),
  CONSTRAINT fk_events_to_location FOREIGN KEY(location_id) REFERENCES location(id),
  CONSTRAINT fk_events_to_categories FOREIGN KEY(category_id) REFERENCES categories(id),
  CONSTRAINT fk_events_to_user FOREIGN KEY(initiator_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS compilations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  pinned BOOLEAN,
  title VARCHAR(255) NOT NULL,
  CONSTRAINT pk_compilations PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS compilations_events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  event_id BIGINT,
  compilations_id BIGINT,
  CONSTRAINT pk_compilations_events PRIMARY KEY(id),
  CONSTRAINT fk_compilations FOREIGN KEY(compilations_id) REFERENCES compilations(id),
  CONSTRAINT fk_events FOREIGN KEY(event_id) REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS requests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE,
  event_id BIGINT NOT NULL,
  requester_id BIGINT NOT NULL,
  status VARCHAR(255) NOT NULL,
  CONSTRAINT pk_requests PRIMARY KEY(id),
  CONSTRAINT fk_requests_to_user FOREIGN KEY(requester_id) REFERENCES users(id),
  CONSTRAINT fk_requests_to_events FOREIGN KEY(event_id) REFERENCES events(id)
);