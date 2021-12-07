drop table stores;
drop table antiques;
drop table bids;

PRAGMA foreign_keys = ON;

CREATE TABLE stores (
    store_id    INTEGER NOT NULL PRIMARY KEY,
    name        TEXT NOT NULL
);

CREATE TABLE antiques(
    antique_id  INTEGER NOT NULL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL,
    pic_url     TEXT NOT NULL,
    price       INTEGER NOT NULL,
    store_id    INTEGER NOT NULL,

    FOREIGN KEY (store_id) REFERENCES stores(store_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE bids (
    bid_id      INTEGER NOT NULL PRIMARY KEY,
    amount      INTEGER NOT NULL,
    antique_id  INTEGER NOT NULL,
    FOREIGN KEY (antique_id) REFERENCES antiques(antique_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- INSERTS
INSERT INTO stores VALUES
( NULL, 'John''s store' ),
( NULL, 'Philips''s store' ),
( NULL, 'Tommy''s store' );

INSERT INTO antiques VALUES
( NULL, 'Shoe', 'Rare shoe from japan', 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,b_rgb:f5f5f5/464e8d65-3a82-472a-aa2c-de53b2dfe7f2/wearallday-shoe-HJSFrd.png', 1000, 1 ),
( NULL, 'Table', 'Victorian era table', 'https://vipp.com/sites/default/files/vipp_packs_large_table_darkoak_03_lowres.jpg', 5250, 1 ),
( NULL, 'Dragon statue', 'Statue from ancient japan', 'https://img.fruugo.com/product/6/99/168070996_max.jpg', 12500, 1 ),
( NULL, 'Tsar egg', 'Diamond encrusted russian egg', 'https://i5.walmartimages.com/asr/5f6e98a2-0e4f-4621-a80b-4cb8260e7e14.b5a3e5da29c3869030faf670df5443f9.jpeg?odnHeight=612&odnWidth=612&odnBg=FFFFFF', 45000, 1 ),
( NULL, 'American classic', 'Classic from 1960', 'https://cdn2.lamag.com/wp-content/uploads/sites/6/2021/09/Black-Car_RezUp-slight_SILO_1500x1000px-1068x712.jpg', 250000, 1 ),

( NULL, 'Revolver', 'Victorian era revolver', 'https://cdn.shopify.com/s/files/1/1524/1342/products/ON9889__01.jpg?v=1604197881', 5000, 2 ),
( NULL, 'Hat', 'Hat from 1870''s', 'https://gambleandgunn.com/wp-content/uploads/2019/05/DSC_5244-560x560.png', 8025, 2 ),
( NULL, 'Brush', 'Brush from 1750''s england', 'https://gambleandgunn.com/wp-content/uploads/2019/05/DSC_5424-560x560.png', 5000, 2 ),
( NULL, 'Lamp', 'Old school lamp', 'https://cdn5.mystore4.no/thumb/333_500/hbygg/97102_Light___Living_Kalym_lampefot_antique_bronze_1.jpg', 1000, 2 ),
( NULL, 'Clock', 'Old clock from germany', 'https://medias.pylones.com/4843-large_default/colortime-alarm-clock.jpg', 6522, 2 ),

( NULL, 'Thunderbird', 'Old ford classic', 'https://www.erclassics.com/media/catalog/product/cache/2/image/700x/17f82f742ffe127f42dca9de82fb58b1/f/o/ford-thunderbird-1955-f9452-037.jpg', 250000, 3 ),
( NULL, 'Mercedes SL 300 Gullwing', 'James bond car', 'https://hips.hearstapps.com/toc.h-cdn.co/assets/16/14/3200x1600/landscape-1459816624-1954-mercedes-300sl-gullwing-a.jpg?resize=980:*', 300000, 3 ),
( NULL, 'Ferrari 250 GTO', 'Fastest ferrari of the 1960s', 'https://hips.hearstapps.com/toc.h-cdn.co/assets/16/14/2560x1820/1962-ferrari-250-gto_1.jpg?resize=980:*', 500000, 3 ),
( NULL, 'Acura NSX', 'Car from 1990s', 'https://hips.hearstapps.com/toc.h-cdn.co/assets/16/14/2560x1706/2005-acura-nsx_1.jpg?resize=980:*', 125000, 3 ),
( NULL, 'Shelby GT350', 'American classic', 'https://hips.hearstapps.com/toc.h-cdn.co/assets/16/18/1962-shelby-cobra-a.jpg?crop=1xw:1.0xh;center,top&resize=980:*', 750000, 3 );