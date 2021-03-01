<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20210301132039 extends AbstractMigration
{
    public function getDescription() : string
    {
        return '';
    }

    public function up(Schema $schema) : void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE hotel ADD image_name VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE reservationhotel DROP FOREIGN KEY fk_client');
        $this->addSql('ALTER TABLE reservationhotel DROP FOREIGN KEY fk_hotelres');
        $this->addSql('ALTER TABLE reservationhotel CHANGE id_hotel id_hotel INT DEFAULT NULL, CHANGE id_user id_user INT DEFAULT NULL');
        $this->addSql('ALTER TABLE reservationhotel ADD CONSTRAINT FK_484FE3AA6B3CA4B FOREIGN KEY (id_user) REFERENCES client (id)');
        $this->addSql('ALTER TABLE reservationhotel ADD CONSTRAINT FK_484FE3AAEDD61FE9 FOREIGN KEY (id_hotel) REFERENCES hotel (id_hotel)');
        $this->addSql('ALTER TABLE service DROP FOREIGN KEY fk_hotel');
        $this->addSql('ALTER TABLE service CHANGE idhotel idhotel INT DEFAULT NULL');
        $this->addSql('ALTER TABLE service ADD CONSTRAINT FK_E19D9AD2D55632C0 FOREIGN KEY (idhotel) REFERENCES hotel (id_hotel)');
    }

    public function down(Schema $schema) : void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE hotel DROP image_name');
        $this->addSql('ALTER TABLE reservationhotel DROP FOREIGN KEY FK_484FE3AA6B3CA4B');
        $this->addSql('ALTER TABLE reservationhotel DROP FOREIGN KEY FK_484FE3AAEDD61FE9');
        $this->addSql('ALTER TABLE reservationhotel CHANGE id_user id_user INT NOT NULL, CHANGE id_hotel id_hotel INT NOT NULL');
        $this->addSql('ALTER TABLE reservationhotel ADD CONSTRAINT fk_client FOREIGN KEY (id_user) REFERENCES client (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE reservationhotel ADD CONSTRAINT fk_hotelres FOREIGN KEY (id_hotel) REFERENCES hotel (id_hotel) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE service DROP FOREIGN KEY FK_E19D9AD2D55632C0');
        $this->addSql('ALTER TABLE service CHANGE idhotel idhotel INT NOT NULL');
        $this->addSql('ALTER TABLE service ADD CONSTRAINT fk_hotel FOREIGN KEY (idhotel) REFERENCES hotel (id_hotel) ON UPDATE CASCADE ON DELETE CASCADE');
    }
}
