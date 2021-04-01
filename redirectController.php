<?php


namespace App\Controller;


use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;

use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class redirectController extends AbstractController
{

//    /**
//     * @Route("/frontacceuil", name="frontacceuil")
//     */
//    public function frontend(): Response
//    {
//        return $this->render('frontend/frontbody.html.twig', [
//            'controller_name' => 'RedirectController',
//                'page_title' => 'Acceuil'
//        ]);
//    }

    /**
     * @Route("/backacceuil", name="backacceuil")
     */
    public function backend(): Response
    {
        return $this->render('backend/backbody.html.twig', [
            'controller_name' => 'RedirectController',
        ]);
    }
}