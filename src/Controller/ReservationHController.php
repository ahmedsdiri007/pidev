<?php
namespace App\Controller;

use App\Entity\Hotel;
use App\Entity\Reservationhotel;
use App\Form\HotelType;
use App\Form\ReservationHType;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Snappy\Pdf;

class ReservationHController extends AbstractController
{
    /**
     * @Route("/reservation/add_reservationHotel/{idHotel}", name="add_reservationHotel")
     */
    public function addReservationHotel(Request $request,$idHotel): Response
    {
        $reservationh = new Reservationhotel();
        $form = $this->createForm(ReservationHType::class, $reservationh);
        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()) {

            $entityManager = $this->getDoctrine()->getManager();
            $hotel = $entityManager->getRepository(Hotel::class)->find($idHotel);
            $reservationh->setIdHotel($hotel);
            $reservationh->setIdUser(null);
            $entityManager->persist($reservationh);

           /* $basic  = new \Nexmo\Client\Credentials\Basic('e6d685d1', 'C8cBct475EEkdvXD');
          $client = new \Nexmo\Client($basic);

          $message = $client->message()->send([
          'to' => '21650750929',
               'from' => 'Vonage APIs',
               'text' => 'reservation effectué avec success'
            ]);*/
            $entityManager->flush();

            return $this->redirectToRoute('list_reservationhotel');

        }

        return $this->render("reservationhotel/reserverhotel.html.twig", [
            "form_title" => "Ajouter une Reservation",
            "form" => $form->createView(),
        ]);
    }
    /**
     * @Route("/reservationh/listReservationh", name="list_reservationhotel")
     */
    public function listService(Request $request, PaginatorInterface $paginator)
    {
        $Reservationhotel = $this->getDoctrine()->getRepository(Reservationhotel::class)->findAll();

        return $this->render("reservationhotel/detailreservation.html.twig", [
            "reservationhotel" => $Reservationhotel,
        ]);
        $pagination = $paginator->paginate(
            Reservationhotel,
            $request->query->getInt('page', 1), /*page number*/
            2 /*limit per page*/
        );

    }
    /**
     * @Route("/reservationh/delete_reservationh/{idReserv}", name="delete_reservationh")
     */
    public function deletereservationh(int $idReserv): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $reservationh = $entityManager->getRepository(Reservationhotel::class)->find($idReserv);
        $entityManager->remove($reservationh);
        $entityManager->flush();
        return $this->redirectToRoute('list_reservationhotel');
    }
    /**
     * @Route("/reservationh/edit_reservationh/{idReserv}", name="edit_reservationh")
     */
    public function editreservqationh(Request $request, $idReserv): Response
    {

        $entityManager = $this->getDoctrine()->getManager();

        $reservationhotel = $entityManager->getRepository(Reservationhotel::class)->find($idReserv);
        $form = $this->createForm(ReservationHType::class, $reservationhotel);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid())
        {
            $entityManager->flush();
            return $this->redirectToRoute('list_reservationhotel');
        }

        return $this->render("reservationhotel/editreservation.html.twig", [
            "form_title" => "Modifier Reservation",
            "form" => $form->createView(),
        ]);
    }
    /**
     * @Route("/reservationhback/listReservationhb", name="list_reservationhotelback")
     */
    public function reservationlist(Request $request, PaginatorInterface $paginator)
    {
        $Reservationhotel = $this->getDoctrine()->getRepository(Reservationhotel::class)->findAll();
$date = new \DateTime('now');


        $pagination = $paginator->paginate(
            $Reservationhotel,
            $request->query->getInt('page', 1), /*page number*/
            2 /*limit per page*/
        );
        if ($request->getMethod() == Request::METHOD_GET) {

            if ($request->get('TELECHARGER_PDF') == "TELECHARGER_PDF") {

                $snappy = new Pdf('C:\wkhtmltopdf\bin\wkhtmltopdf');
                $html = "<style>
          .clearfix:after {
  content: \"\";
  display: table;
  clear: both;
}
a {
  color: #5D6975;
  text-decoration: underline;
}
body {
  position: relative;
  width: 21cm;  
  height: 29.7cm; 
  margin: 0 auto; 
  color: #001028;
  background: #FFFFFF; 
  font-family: Arial, sans-serif; 
  font-size: 12px; 
  font-family: Arial;
  background-image: url(https://images.unsplash.com/photo-1508615070457-7baeba4003ab?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1950&q=80);
}
header {
  padding: 10px 0;
  margin-bottom: 30px;
}
#logo {
  text-align: center;
  margin-bottom: 10px;
}
#logo img {
  width: 90px;
}
h1 {
  border-top: 1px solid  #5D6975;
  border-bottom: 1px solid  #5D6975;
  color: #5D6975;
  font-size: 2.4em;
  line-height: 1.4em;
  font-weight: normal;
  text-align: center;
  margin: 0 0 20px 0;
background: url(https://images.unsplash.com/photo-1579546929662-711aa81148cf?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1050&q=80);

}
#project {
  float: left;
}
#project span {
  color: #5D6975;
  text-align: right;
  width: 52px;
  margin-right: 10px;
  display: inline-block;
  font-size: 0.8em;
}
#company {
  float: right;
  text-align: right;
}
#project div,
#company div {
  white-space: nowrap;        
}
table {
  width: 100%;
  border-collapse: collapse;
  border-spacing: 0;
  margin-bottom: 20px;
}
table tr:nth-child(2n-1) td {
  background: #F5F5F5;
}
table th,
table td {
  text-align: center;
}
table th {
  padding: 5px 20px;
  color: #5D6975;
  border-bottom: 1px solid #C1CED9;
  white-space: nowrap;        
  font-weight: normal;
}
table .service,
table .desc {
  text-align: left;
}
table td {
  padding: 20px;
  text-align: right;
}
table td.service,
table td.desc {
  vertical-align: top;
}
table td.unit,
table td.qty,
table td.total {
  font-size: 1.2em;
}
table td.grand {
  border-top: 1px solid #5D6975;;
}
#notices .notice {
  color: #5D6975;
  font-size: 1.2em;
}
footer {
  color: #5D6975;
  width: 100%;
  height: 30px;
  position: absolute;
  bottom: 0;
  border-top: 1px solid #C1CED9;
  padding: 8px 0;
  text-align: center;
}</style>
<!DOCTYPE html>
<html lang=\"en\">
  <head>
    <meta charset=\"utf-8\">
    <title>Example 1</title>
    <link rel=\"stylesheet\" media=\"all\" />
  </head>
  <body>
    <header class=\"clearfix\">
      <div id=\"logo\">
        
      </div>
      <h1>RESERVATIONS</h1>
      <div id=\"company\" class=\"clearfix\">
        <div>Cycle.tn</div>
        <div>455 Foggy Heights,<br /> AZ 85004, US</div>
        <div>(602) 519-0450</div>
        
      </div>
      
    </header>
    <main>
      <table>
        <thead>
          <tr>
            <th>NBNuit</th>
            <th>NBChambre</th>
            <th>Type</th>
            <th>Date</th>
            <th>NbPersonne</th>
           </tr>
        </thead>
        <tbody>";
                foreach ($Reservationhotel as $r) {
                    $html = $html . "<tr>
            <td>" . $r->getNbNuit() . "</td>
            <td >" . $r->getNbChambre() . "</td>
            <td>" . $r->getType() . "</td>
            <td>" . $r->getNbPersonne() . "</td>
          </tr>";

                }
                $html = $html . "
          
        </tbody>
      </table>
      
    </main>
    <footer>
     RESERVATIONS
    </footer>
  </body>
</html>";
                $snappy->generateFromHtml($html, 'C:\wamp64\www\pidev\pdf\ListeReservation'.$date->format("y/M/d").'.pdf');



            }
        }
        return $this->render('reservationhotel/listereservationback.html.twig', [
            "Reservationhotel" => $pagination,
        ]);
    }



}