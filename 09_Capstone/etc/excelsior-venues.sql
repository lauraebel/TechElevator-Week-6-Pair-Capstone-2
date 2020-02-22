SELECT venue.id AS venue_id, venue.name AS venue_name, venue.city_id AS city_id, venue.description AS venue_description, city.name AS city_name, state.abbreviation AS state_abbreviation, category.name AS category_name
FROM venue
JOIN city ON city_id = city.id 
JOIN state ON city.state_abbreviation = state.abbreviation
JOIN category_venue ON venue_id = category_venue.venue_id
JOIN category ON category_venue.category_id = category.id;