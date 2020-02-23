SELECT venue.id AS venue_id, venue.name AS venue_name, venue.city_id AS city_id, venue.description AS venue_description, city.name AS city_name, state.abbreviation AS state_abbreviation, category.name AS category_name
FROM venue
JOIN city ON city_id = city.id 
JOIN state ON city.state_abbreviation = state.abbreviation
JOIN category_venue ON venue_id = category_venue.venue_id
JOIN category ON category_venue.category_id = category.id;

SELECT * FROM space;

SELECT space.id AS space_id, space.venue_id AS venue_id, space.name AS space_name, space.open_from AS open_from,
space.open_to AS open_to, space.daily_rate AS daily_rate, space.max_occupancy AS max_occupancy
FROM space
JOIN venue ON space.venue_id = venue.id;
